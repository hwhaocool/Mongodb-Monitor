package com.github.hwhaocool.mm.service.opalarm;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;

public interface IAlarm {
    
    public boolean match(SlowOpRecordDocument doc);
    
    public String tips();
    
    public SlowOpRecordDocument getDoc();

}
