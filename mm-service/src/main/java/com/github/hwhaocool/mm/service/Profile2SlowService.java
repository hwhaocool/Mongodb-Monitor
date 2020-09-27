package com.github.hwhaocool.mm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.github.hwhaocool.mm.service.alarm.MatchRuleTmp;
import com.github.hwhaocool.mm.service.ana.AnalysisService;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.opalarm.impl.DocsSacnTooMuch;
import com.github.hwhaocool.mm.service.opalarm.impl.IndexMiss;
import com.github.hwhaocool.mm.service.opalarm.impl.IndexPart;
import com.github.hwhaocool.mm.service.opalarm.impl.ReturnTooLong;
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
        
        // 2. 转成doc
        List<SlowOpRecordDocument> docList = list.stream()
                .map( x -> genDoc(x))
                .collect(Collectors.toList());
        
        // 3. 剔除 sha1 一致的记录
        // 在并发的时候，可能会出现， 查询条件不一样，但是 时间、用户、耗时、放弃次数等一致的情况，不想深究，直接只保留一个
        List<SlowOpRecordDocument> collect = docList.stream()
            .distinct()
            .collect(Collectors.toList());
        
        //4. 何时需要去保存？ 1）未分析过的 2）需要报警的 or 耗时较长的
        
        // 4.1 未分析过的（未重复）
        // 数据可能重复(因为是 固定集合)，需要根据 sha1 字段进行数据库去重
        List<SlowOpRecordDocument> sha1UniqueList = getSha1UniqueList(collect);
        if (CollectionUtils.isEmpty(sha1UniqueList)) {
            return;
        }
        
        // 4.2 满足告警规则
        List<MatchRuleTmp> alarmList = getNeedAlarmList(sha1UniqueList);
        
        // or 耗时较长的
        List<SlowOpRecordDocument> costList = sha1UniqueList.stream()
            .filter(x -> isCostTimeMatchCondition(x))
            .collect(Collectors.toList());
        
        // 4.2 合起来
        Set<SlowOpRecordDocument> saveSet = new HashSet<>();
        saveSet.addAll(alarmList.stream().map(MatchRuleTmp::getDoc).collect(Collectors.toList()));
        saveSet.addAll(costList);
        
        //5. 保存
        for (SlowOpRecordDocument saveDoc : saveSet) {
            try {
                slowOpRecordDAO.save(saveDoc);
            } catch (Exception e) {
                LOGGER.error("save error, {}", saveDoc);
            }
            
        }
//        slowOpRecordDAO.saves(saveSet);
        
        // 6. 告警
        genAndSendAlarm(alarmList);
        
        LOGGER.info("recordAndSave2Other end, save {} doc", sha1UniqueList.size());
    }
    
    private void genAndSendAlarm(List<MatchRuleTmp> list) {
        analysisService.analysisAndAlarm(list);
    }
    
    /**
     * <br>得到需要告警的 doc列表
     *
     * @param list
     * @return
     * @author YellowTail
     * @since 2020-01-02
     */
    private List<MatchRuleTmp> getNeedAlarmList(List<SlowOpRecordDocument> list) {
        
        return list.stream()
            .map(doc -> {
                
                List<IAlarm> checkerList = new ArrayList<>();
                checkerList.add(new IndexMiss(thresholdService));
                checkerList.add(new IndexPart(thresholdService));
                checkerList.add(new DocsSacnTooMuch(thresholdService));
                checkerList.add(new ReturnTooLong(thresholdService));
                
                // 如果匹配，返回 tmp 对象， 如果不匹配，返回null
                return checkerList.stream()
                    .filter(k -> k.match(doc)  )
                    .findAny()
                    .map(k ->  new MatchRuleTmp(doc, k))
                    .orElse(null);
            })
            .filter(k -> null != k)               //剔除 null 对象
            .collect(Collectors.toList());
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
        document.setSha1(genSha1(document));
        
        document.set_id(new ObjectId());
        
        //表名处理一下， ns  
        String ns = document.getNs();
        
        String[] split = ns.split("\\.");
        String dbName = split[0];
        String collectionName = split[1];
        document.setNs(collectionName);
        
        //用户处理一下
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
    private String genSha1(SlowOpRecordDocument document) {
        //在哪个时间{ts} 谁{user} 对谁{ns} 进行了什么操作{op}， 花了多久{millis} 数据库放弃了多少次{numYield}
        
        Date createTime = document.getCreateTime();
        String user = document.getUser();
        String ns =  Optional.ofNullable(document.getNs()).orElse("null");
        String op = document.getOp();
        
        DateTime dateTime = new DateTime(createTime);
        String dateStr = dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS");
        
        String combineStr = String.format("%s%s%s%s%d%d", dateStr, user, ns, op, document.getMillis(), document.getNumYield());
        
        return DigestUtils.sha1Hex(combineStr);
    }
}
