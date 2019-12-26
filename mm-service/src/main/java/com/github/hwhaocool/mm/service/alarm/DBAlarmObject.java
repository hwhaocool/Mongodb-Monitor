package com.github.hwhaocool.mm.service.alarm;

public class DBAlarmObject extends AlarmObject{

    protected String getBusinessName() {
        return "数据库慢查询监控";
    }
    
    public void setTips(String beanName, String methodName) {
        alarmInfo.put("tips", "一次性任务执行情况通知");
        alarmInfo.put("beanName", beanName);
        alarmInfo.put("methodName", methodName);
    }
    
    public DBAlarmObject recordId(String id) {
        alarmInfo.put("ObjectId", id);
        return this;
    }
    
    public DBAlarmObject tips(String tips) {
        alarmInfo.put("tips", tips);
        return this;
    }

}
