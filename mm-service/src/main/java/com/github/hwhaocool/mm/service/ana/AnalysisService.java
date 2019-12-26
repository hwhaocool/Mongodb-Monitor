package com.github.hwhaocool.mm.service.ana;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.alarm.AlarmSendService;
import com.github.hwhaocool.mm.service.alarm.DBAlarmObject;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;
import com.github.hwhaocool.mm.service.opalarm.impl.DocsSacnTooMuch;
import com.github.hwhaocool.mm.service.opalarm.impl.IndexMiss;
import com.github.hwhaocool.mm.service.opalarm.impl.IndexPart;

@Service
public class AnalysisService {
    
    @Autowired
    private AlarmSendService alarmSendService;
    
    public void analysisAndAlarm(List<SlowOpRecordDocument> list) {
        
        List<IAlarm> checkerList = new ArrayList<>();
        checkerList.add(new IndexMiss());
        checkerList.add(new IndexPart());
        checkerList.add(new DocsSacnTooMuch());
        
        list.forEach(doc -> {
            checkerList.stream()
                .filter(k -> k.match(doc)  )
                .findAny()
                .ifPresent(k ->  sendAlarm(doc, k ));
        });
    }
    
    private void sendAlarm(SlowOpRecordDocument doc, IAlarm checker) {
        
        DBAlarmObject alarmObject = new DBAlarmObject()
                .recordId(doc.get_id())
                .tips(checker.tips());
        
        alarmSendService.sendAlarm(alarmObject);
    }

}
