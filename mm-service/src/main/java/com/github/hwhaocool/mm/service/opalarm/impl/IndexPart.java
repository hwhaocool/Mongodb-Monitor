package com.github.hwhaocool.mm.service.opalarm.impl;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;

/**
 * <br>索引部分未命中(部分字段没加索引) 
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class IndexPart  implements IAlarm  {

    public boolean match(SlowOpRecordDocument doc) {
        if (doc.getDocsExamined() > doc.getKeysExamined()) {
            return true;
        }
        
        if (doc.getPlanSummary().startsWith("COLLSCAN")) {
            return true;
        }
        
        return false;
    }

    public String tips() {
        return "索引有点问题，查询语句没有完全走索引";
    }

}
