package com.github.hwhaocool.mm.service.opalarm.impl;

import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.threshold.AlarmThreshold;

/**
 * <br>扫描文档过多， 要么是代码有问题，要么是产品设计有问题
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class DocsSacnTooMuch  implements IAlarm  {

    public boolean match(SlowOpRecordDocument doc) {
        return doc.getDocsExamined() > AlarmThreshold.getDocsThreshold();
    }

    public String tips() {
        return "扫描文档数过多，代码or产品设计有问题";
    }

}
