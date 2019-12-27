package com.github.hwhaocool.mm.service.alarm;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AlarmSendService {
    
    /**
     * 机器人id
     */
    @Value("${common.robot-key}")
    public String robotKey;
    
    /**
     * 当前环境，比如 local dev等
     */
    @Value("${common.env-name}")
    private String envName;
    
    public void sendAlarm(AlarmObject alarmObject) {
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
