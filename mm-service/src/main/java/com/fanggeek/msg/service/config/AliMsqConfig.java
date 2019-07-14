package com.fanggeek.msg.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AliMsq 相关配置类（至于为什么叫msq 我也不懂，只是迁移时配置就是这么写的，只是用于推送微信消息进用到的）
 * @Author: AndrewYan
 * @Date: 2019/6/27 10:47
 */
@Configuration
@ConfigurationProperties(prefix = "alimsq")
public class AliMsqConfig {

    private String recvName;

    private String sendName;

    private String topicUnitCreateName;

    private String topicName;

    private String env;

    private String wechatName;

    private String weworkName;

    private String mpwechatName;

    private String websocketName;

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getTopicUnitCreateName() {
        return topicUnitCreateName;
    }

    public void setTopicUnitCreateName(String topicUnitCreateName) {
        this.topicUnitCreateName = topicUnitCreateName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getWeworkName() {
        return weworkName;
    }

    public void setWeworkName(String weworkName) {
        this.weworkName = weworkName;
    }

    public String getMpwechatName() {
        return mpwechatName;
    }

    public void setMpwechatName(String mpwechatName) {
        this.mpwechatName = mpwechatName;
    }

    public String getWebsocketName() {
        return websocketName;
    }

    public void setWebsocketName(String websocketName) {
        this.websocketName = websocketName;
    }

    @Override
    public String toString() {
        return "AliMsqConfig{" +
                "recvName='" + recvName + '\'' +
                ", sendName='" + sendName + '\'' +
                ", topicUnitCreateName='" + topicUnitCreateName + '\'' +
                ", topicName='" + topicName + '\'' +
                ", env='" + env + '\'' +
                ", wechatName='" + wechatName + '\'' +
                ", weworkName='" + weworkName + '\'' +
                ", mpwechatName='" + mpwechatName + '\'' +
                ", websocketName='" + websocketName + '\'' +
                '}';
    }
}
