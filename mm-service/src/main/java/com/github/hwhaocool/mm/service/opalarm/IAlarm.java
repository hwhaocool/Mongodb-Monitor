package com.github.hwhaocool.mm.service.opalarm;

import com.fanggeek.mm.dao.model.doc.SlowOpRecordDocument;

public interface IAlarm {
    
    public boolean match(SlowOpRecordDocument doc);
    
    public String tips();

}
