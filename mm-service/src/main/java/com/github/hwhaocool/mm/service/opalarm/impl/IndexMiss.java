package com.github.hwhaocool.mm.service.opalarm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.threshold.ThresholdService;

/**
 * <br>索引完全未命中(所有查询字段都没加索引) 
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class IndexMiss implements IAlarm {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexMiss.class);
    
    private ThresholdService thresholdService;
    
    private SlowOpRecordDocument doc;
    
    public IndexMiss(ThresholdService service) {
        thresholdService = service;
    }

    public boolean match(SlowOpRecordDocument doc) {
        this.doc = doc;
        //没有走索引
        // 当 keysExamined 等于0的时候，可能 docsExamined 也为0， 这种场景 暂时放过
        //还有场景就是 集合本身数据比较少(阈值控制)，全表扫描就扫描吧，问题也不大，放过
        
        if (doc.getKeysExamined() == 0) {
            if (doc.getDocsExamined() >= thresholdService.getKeyDocsScanGap().intValue()) {
                return true;
            }
        }
        
        return false;
    }
    
    public SlowOpRecordDocument getDoc() {
        return doc;
    }

    public String tips() {
        LOGGER.info("IndexMiss {}", doc);
        
        return "天啦撸！ 没有加索引！, [条件] keysExamined == 0, 帐号 = " + doc.getUser();
    }

    

}
