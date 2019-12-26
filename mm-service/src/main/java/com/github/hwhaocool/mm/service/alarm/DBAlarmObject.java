package com.github.hwhaocool.mm.service.alarm;

import java.util.AbstractMap;

import org.apache.commons.lang3.StringUtils;

import com.github.hwhaocool.mm.common.constants.HostUtils;

public class DBAlarmObject extends AlarmObject{

    protected String getBusinessName() {
        return "数据库慢查询监控";
    }
    
    public DBAlarmObject recordId(String id) {
        alarmInfo.put("ObjectId", id);
        
        String host = HostUtils.getHost();
        if (StringUtils.isNotBlank(host)) {
            
            String url = String.format("%s/api/slow/details?id=%s", host, id);
            
            jumpUrl = new AbstractMap.SimpleEntry<String, String>("click here to see details", url);
        }
        
        return this;
    }
    
    public DBAlarmObject tips(String tips) {
        alarmInfo.put("tips", tips);
        return this;
    }

}
