package com.github.hwhaocool.mm.service.opalarm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.threshold.ThresholdService;

/**
 * <br>扫描文档过多， 要么是代码有问题，要么是产品设计有问题
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class DocsSacnTooMuch  implements IAlarm  {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DocsSacnTooMuch.class);
    
    private ThresholdService thresholdService;
    
    private SlowOpRecordDocument document;
    
    public DocsSacnTooMuch(ThresholdService service) {
        thresholdService = service;
    }

    public boolean match(SlowOpRecordDocument doc) {
        //doc 存下来，提示信息要用到
        document = doc;
        
        return doc.getDocsExamined() > thresholdService.getDocsExaminedThreshold();
    }

    public String tips() {
        LOGGER.info("DocsSacnTooMuch {}", document);
        
        return String.format("扫描文档数过多[%d 万]，代码or产品设计有问题, [条件]docsExamined 大于阈值, 帐号 = %s", 
                document.getDocsExamined() / 10000, document.getUser());
    }

    public SlowOpRecordDocument getDoc() {
        return document;
    }

}
