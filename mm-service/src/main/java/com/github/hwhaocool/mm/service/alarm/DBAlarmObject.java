package com.github.hwhaocool.mm.service.alarm;

import java.util.AbstractMap;

import org.apache.commons.lang3.StringUtils;

public class DBAlarmObject extends AlarmObject{

    protected String getBusinessName() {
        return "数据库慢查询监控";
    }
    
    public DBAlarmObject recordId(String id) {
        alarmInfo.put("ObjectId", id);
        
        return this;
    }
    
    public DBAlarmObject jumpHost(String id, String host) {
        if (StringUtils.isBlank(host) || StringUtils.isBlank(id)) {
            return this;
        }
        
        String url = String.format("%s/api/mm/slow/details?id=%s", host, id);
        
        jumpUrl = new AbstractMap.SimpleEntry<String, String>("click me to see details", url);
        
        return this;
    }
    
    public DBAlarmObject tips(String tips) {
        alarmInfo.put("tips", tips);
        return this;
    }

}
