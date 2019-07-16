package com.fanggeek.mm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanggeek.common.json.JSON2Helper;
import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;
import com.fanggeek.mm.db.dao.ProfileDAO;
import com.fanggeek.mm.db.dao.SlowOpRecordDAO;
import com.mongodb.BasicDBObject;

@Service
public class Profile2SlowService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Profile2SlowService.class);

    @Autowired
    private ProfileDAO profileDAO;
    
    @Autowired
    private SlowOpRecordDAO slowOpRecordDAO;

    /**
     * <br> 读取 profile 并记录下来
     *
     * @author YellowTail
     * @since 2019-07-16
     */
    public void recordAndSave2Other() {
        LOGGER.info("RecordSystemProfileTask recordAndSave2Other");
        
        //全部查出来
        //因为是一个 capped collection，固定集合，大小固定为1M，所以内存不会有什么问题
        List<BasicDBObject> list = profileDAO.getList(0);
        
        LOGGER.info("recordAndSave2Other length is {}", list.size());
        
        List<SlowOpRecordDocument> collect = list.stream()
                .map( x -> genDoc(x))
                //耗时 增加条件，不一定所有的都记录
                .filter(x -> isCostTimeMatchCondition(x))
                //因为是固定集合，所以当慢查询不多的时候，很容易就重复了，需要排除一下
                .filter( x -> saveBySha1Consition(x) )
                .collect(Collectors.toList());
        
        slowOpRecordDAO.saves(collect);
        
        LOGGER.info("recordAndSave2Other end, save {} doc", collect.size());
    }
    
    /**
     * <br>慢查询耗时是否匹配我们设置的阈值
     * <br>阈值配置在环境变量里， 
     *
     * @param document
     * @return
     * @author YellowTail
     * @since 2019-07-16
     */
    private boolean isCostTimeMatchCondition(SlowOpRecordDocument document) {
        Map<String, String> getenv = System.getenv();
        
        String minCost = getenv.getOrDefault("min-cost", "1000");
        
        int limit = 1000;
        try {
            limit = Integer.parseInt(minCost);
        } catch (NumberFormatException e) {
            LOGGER.info("env min-cost {} is invalid number format ", minCost);
        }
        
        LOGGER.info("current min cost limit is {}", limit);
        
        Integer millis = document.getMillis();
        if (null == millis) {
            return false;
        }
        
        if (millis.intValue() >= limit) {
            return true;
        }
        
        return false;
    }
    
    /**
     * <br>sha1 不重复的时候，可以保存
     *
     * @param document
     * @return
     * @author YellowTail
     * @since 2019-07-16
     */
    private boolean saveBySha1Consition(SlowOpRecordDocument document) {
        boolean sha1Exist = slowOpRecordDAO.isSha1Exist(document.getSha1());
        
        if (sha1Exist) {
            LOGGER.info("sha1 {} duplicate,", document.getSha1());
        }
        
        return ! sha1Exist;
    }
    
    /**
     * <br>profile 转成 doc
     *
     * @param basicDBObject
     * @return
     * @author YellowTail
     * @since 2019-07-16
     */
    private SlowOpRecordDocument genDoc(BasicDBObject basicDBObject) {
        
        String json = JSON2Helper.toJson(basicDBObject);
        SlowOpRecordDocument document = JSON2Helper.toObject(json, SlowOpRecordDocument.class);
        
        //设置 sha1
        genAndSetHashCode(document);
        
        document.set_id(new ObjectId());
        
        //表名处理一下， ns  fanggeek.odin_checkins
        String ns = document.getNs();
        
        String[] split = ns.split("\\.");
        String dbName = split[0];
        String collectionName = split[1];
        document.setNs(collectionName);
        
        //用户处理一下，fanggeek_frontend_trident@fanggeek
        String user = document.getUser();
        String replace = user.replace("@" + dbName, "");
        document.setUser(replace);
        
        return document;
    }
    
    /**
     * <br>生成并设置 sha1
     *
     * @param document
     * @author YellowTail
     * @since 2019-07-16
     */
    private void genAndSetHashCode(SlowOpRecordDocument document) {
        //在哪个时间{ts} 谁{user} 对谁{ns} 进行了什么操作{op}
        
        Date createTime = document.getCreateTime();
        String user = document.getUser();
        String ns = document.getNs();
        String op = document.getOp();
        
        DateTime dateTime = new DateTime(createTime);
        String dateStr = dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS");
        
        String combineStr = String.format("%s%s%s%s", dateStr, user, ns, op);
        
        String sha1Hex = DigestUtils.sha1Hex(combineStr);
        
        document.setSha1(sha1Hex);
    }
}
