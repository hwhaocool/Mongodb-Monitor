package com.github.hwhaocool.mm.service.opalarm.impl;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;

/**
 * <br>索引完全未命中(所有查询字段都没加索引) 
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class IndexMiss implements IAlarm {

    public boolean match(SlowOpRecordDocument doc) {
        //没有走索引
        return doc.getKeysExamined() == 0;
    }

    public String tips() {
        return "天啦撸！ 没有加索引！";
    }

}
