package com.github.hwhaocool.mm.service.alarm;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;

/**
 * <br>匹配告警规则的 临时对象
 *
 * @author YellowTail
 * @since 2020-01-02
 */
public class MatchRuleTmp {
    
    private SlowOpRecordDocument doc;
    
    private IAlarm checker;
    
    public MatchRuleTmp(SlowOpRecordDocument doc, IAlarm checker) {
        this.doc = doc;
        this.checker = checker;
    }

    public SlowOpRecordDocument getDoc() {
        return doc;
    }

    public void setDoc(SlowOpRecordDocument doc) {
        this.doc = doc;
    }

    public IAlarm getChecker() {
        return checker;
    }

    public void setChecker(IAlarm checker) {
        this.checker = checker;
    }

    @Override
    public String toString() {
        return "MatchRuleTmp [doc=" + doc + ", checker=" + checker + "]";
    }
    

}
