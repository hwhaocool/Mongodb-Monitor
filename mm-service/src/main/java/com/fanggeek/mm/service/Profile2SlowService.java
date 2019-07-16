package com.fanggeek.mm.service;

import java.util.Date;
import java.util.List;
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
                //因为是固定集合，所以当慢查询不多的时候，很容易就重复了，需要排除一下
                .filter( x -> slowOpRecordDAO.isSha1Exist(x.getSha1()) )
                .collect(Collectors.toList());
        
        slowOpRecordDAO.saves(collect);
        
        LOGGER.info("recordAndSave2Other end, save {} doc", collect.size());
    }
    
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
