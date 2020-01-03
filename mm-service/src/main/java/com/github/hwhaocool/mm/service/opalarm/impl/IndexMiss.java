package com.github.hwhaocool.mm.service.opalarm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;

/**
 * <br>索引完全未命中(所有查询字段都没加索引) 
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class IndexMiss implements IAlarm {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexMiss.class);
    
    private SlowOpRecordDocument doc;

    public boolean match(SlowOpRecordDocument doc) {
        this.doc = doc;
        //没有走索引
        return doc.getKeysExamined() == 0;
    }
    
    public SlowOpRecordDocument getDoc() {
        return doc;
    }

    public String tips() {
        LOGGER.info("IndexMiss {}", doc);
        
        return "天啦撸！ 没有加索引！, [条件] keysExamined == 0, 帐号 = " + doc.getUser();
    }

    

}
