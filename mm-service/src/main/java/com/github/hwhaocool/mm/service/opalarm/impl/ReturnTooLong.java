package com.github.hwhaocool.mm.service.opalarm.impl;

import com.github.hwhaocool.mm.common.utils.RadixUtils;
import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.threshold.ThresholdService;

/**
 * <br>查询的时候 一次性 查的太多了
 *
 * @author YellowTail
 * @since 2020-01-02
 */
public class ReturnTooLong implements IAlarm {
    
    private SlowOpRecordDocument doc;
    
    private ThresholdService thresholdService;
    
    public ReturnTooLong(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    public boolean match(SlowOpRecordDocument doc) {
        this.doc = doc;
        
        if (null != doc.getNreturned() && doc.getNreturned().intValue() >= thresholdService.getMaxReturnItem().intValue()) {
            return true;
        }
        
        if (null != doc.getResponseLength() &&  doc.getResponseLength().intValue() >= thresholdService.getMaxReturnLength().intValue()) {
            return true;
        }
        
        return false;
    }

    public String tips() {
        System.out.println(doc.toString());
        return String.format("返回过长！ [条件] nreturned == %d, responseLength == %s", doc.getNreturned(),  RadixUtils.humanRead(doc.getResponseLength().intValue()));
    }

}
