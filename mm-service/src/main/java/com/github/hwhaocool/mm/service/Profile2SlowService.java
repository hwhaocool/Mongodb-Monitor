package com.github.hwhaocool.mm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hwhaocool.mm.common.json.JSON2Helper;
import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.db.dao.ProfileDAO;
import com.github.hwhaocool.mm.db.dao.SlowOpRecordDAO;
import com.github.hwhaocool.mm.service.ana.AnalysisService;
import com.github.hwhaocool.mm.service.threshold.ThresholdService;
import com.mongodb.BasicDBObject;

@Service
public class Profile2SlowService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Profile2SlowService.class);

    @Autowired
    private ProfileDAO profileDAO;
    
    @Autowired
    private SlowOpRecordDAO slowOpRecordDAO;
    
    @Autowired
    private AnalysisService analysisService;
    
    @Autowired
    private ThresholdService thresholdService;

    /**
     * <br> 读取 profile 并记录下来
     *
     * @author YellowTail
     * @since 2019-07-16
     */
    public void recordAndSave2Other() {
        LOGGER.info("RecordSystemProfileTask recordAndSave2Other");
        
        //1. 全部查出来
        //因为是一个 capped collection，固定集合，大小固定为1M，所以内存不会有什么问题
        List<BasicDBObject> list = profileDAO.getList(0);
        
        LOGGER.info("recordAndSave2Other length is {}", list.size());
        
        // 2. 得到 符合慢查询阈值的 doc 列表
        List<SlowOpRecordDocument> matchCostList = list.stream()
                .map( x -> genDoc(x))
                //耗时 增加条件，不一定所有的都记录
                .filter(x -> isCostTimeMatchCondition(x))
                .collect(Collectors.toList());
        
        
        //3. collect 本身的 sha1 是否会重复呢？ (sha1 策略可能存在问题)
        Map<String, List<SlowOpRecordDocument>> collect2 = matchCostList.stream()
            .collect(Collectors.groupingBy(SlowOpRecordDocument::getSha1));
        
        if (collect2.size() != matchCostList.size()) {
            //数量不相等，说明有重复的
            //找到，打印并退出
            collect2.entrySet().stream()
                .filter(e -> (e.getValue().size() > 1) )
                .findAny()
                .ifPresent(e -> {
                    LOGGER.error("found sha1 duplicate! sha1 {}, doc {}", e.getKey(), e.getValue());
                });
            
            return;
        }
        
        // 4. 数据可能重复(因为是 固定集合)，需要根据 sha1 字段进行数据库去重
        List<SlowOpRecordDocument> sha1UniqueList = getSha1UniqueList(matchCostList);
        
        if (CollectionUtils.isEmpty(sha1UniqueList)) {
            return;
        }
        
        //5. 保存
        slowOpRecordDAO.saves(sha1UniqueList);
        
        // 6. 解析 + 告警
        analysisAndAlarm(sha1UniqueList);
        
        LOGGER.info("recordAndSave2Other end, save {} doc", sha1UniqueList.size());
    }
    
    private void analysisAndAlarm(List<SlowOpRecordDocument> list) {
        analysisService.analysisAndAlarm(list);
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
        Integer millis = document.getMillis();
        if (null == millis) {
            return false;
        }
        
        if (millis.intValue() >= thresholdService.getMinCostThreshold()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * <br>得到 sha1 唯一的 document 列表
     *
     * @param matchCostList
     * @return
     * @author YellowTail
     * @since 2019-12-26
     */
    private List<SlowOpRecordDocument> getSha1UniqueList(List<SlowOpRecordDocument> matchCostList) {
        // 1. 当前 doc 的 sha1 列表
        List<String> sha1List = matchCostList.stream()
                .map(SlowOpRecordDocument::getSha1)
                .collect(Collectors.toList());
        
        // 2. 数据库已存在的 sha1 列表
        List<SlowOpRecordDocument> sha1Match = slowOpRecordDAO.sha1Match(sha1List);
        
        if (CollectionUtils.isEmpty(sha1Match)) {
            return matchCostList;
        }
        
        List<String> dbExistSha1List = sha1Match.stream()
            .map(SlowOpRecordDocument::getSha1)
            .collect(Collectors.toList());
        
        // 3. 剔除
        return matchCostList.stream()
            .filter(d -> ! dbExistSha1List.contains(d.getSha1()))
            .collect(Collectors.toList());
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
        //在哪个时间{ts} 谁{user} 对谁{ns} 进行了什么操作{op}， 花了多久{millis} 数据库放弃了多少次{numYield}
        
        Date createTime = document.getCreateTime();
        String user = document.getUser();
        String ns = document.getNs();
        String op = document.getOp();
        
        DateTime dateTime = new DateTime(createTime);
        String dateStr = dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS");
        
        String combineStr = String.format("%s%s%s%s%d%d", dateStr, user, ns, op, document.getMillis(), document.getNumYield());
        
        String sha1Hex = DigestUtils.sha1Hex(combineStr);
        
        document.setSha1(sha1Hex);
    }
}
