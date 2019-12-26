package com.github.hwhaocool.mm.service.alarm;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.hwhaocool.mm.dao.model.doc.SlowOpRecordDocument;
import com.github.hwhaocool.mm.service.opalarm.impl.IndexPart;

@Service
public class AlarmSendService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmSendService.class);
    
    /**
     * 机器人id
     */
    @Value("${common.robotKey}")
    public String robotKey;
    
    /**
     * 当前环境，比如 local dev等
     */
    @Value("${common.envName}")
    private String envName;
    
    public void sendAlarm(AlarmObject alarmObject) {
        if ("local".equals(envName)) {
            return;
        }
        
        if (null == alarmObject) {
            return;
        }
        
        alarmObject.setEnvName(envName);
        
        WebClient.create()
            .post()
            .uri("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key={key}", robotKey)
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(Charset.forName("utf-8"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(alarmObject.getMessage())
            .exchange()                                               // 开始发送
            .subscribe()                                             //静默消费，啥也不干
            ;
        
    }

}
