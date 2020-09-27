package com.github.hwhaocool.mm.service.opalarm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.threshold.ThresholdService;

/**
 * <br>索引部分未命中(部分字段没加索引) 
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class IndexPart  implements IAlarm  {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexPart.class);
    
    private ThresholdService thresholdService;
    
    private SlowOpRecordDocument doc;
    
    public IndexPart(ThresholdService service) {
        thresholdService = service;
    }

    public boolean match(SlowOpRecordDocument doc) {
        this.doc = doc;
        
        if ((doc.getDocsExamined().intValue() - doc.getKeysExamined().intValue()) > thresholdService.getKeyDocsScanGap().intValue()) {
            //在实际中发现，这两个数字经常差距一点点，告警很频繁，所以加了一个阈值
            return true;
        }
        
        if (doc.getPlanSummary().startsWith("COLLSCAN")) {
            return true;
        }
        
        return false;
    }

    public String tips() {
        LOGGER.info("IndexPart {}", doc);
        
        return "索引有点问题，查询语句没有完全走索引, [条件] docsExamined - keysExamined > 阈值, 帐号 = " + doc.getUser();
    }

    public SlowOpRecordDocument getDoc() {
        return doc;
    }

}
