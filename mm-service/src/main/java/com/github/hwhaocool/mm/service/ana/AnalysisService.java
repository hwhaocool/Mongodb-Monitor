package com.github.hwhaocool.mm.service.ana;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.alarm.AlarmSendService;
import com.github.hwhaocool.mm.service.alarm.DBAlarmObject;
import com.github.hwhaocool.mm.service.alarm.MatchRuleTmp;
import com.github.hwhaocool.mm.service.opalarm.IAlarm;

@Service
public class AnalysisService {
    
    @Value("${common.details-host}")
    private String detailsHost;
    
    @Autowired
    private AlarmSendService alarmSendService;
    
    
    public void analysisAndAlarm(List<MatchRuleTmp> list) {
        
        list.forEach(k -> sendAlarm(k.getDoc(), k.getChecker()));
    }
    
    public void sendAlarm(SlowOpRecordDocument doc, IAlarm checker) {
        
        DBAlarmObject alarmObject = new DBAlarmObject()
                .recordId(doc.get_id())
                .jumpHost(doc.get_id(), detailsHost)
                .tips(checker.tips());
        
        alarmSendService.sendAlarm(alarmObject);
    }
    
}
