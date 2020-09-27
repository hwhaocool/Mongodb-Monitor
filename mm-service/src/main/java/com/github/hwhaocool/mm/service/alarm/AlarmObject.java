package com.github.hwhaocool.mm.service.alarm;

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AlarmObject {
    
    public static final String CHANGE_LINE = "\n".intern();
    public static final String TAB_INDENT = "    at ";
    
    /**
     * 最大字节(官方支持 2048，我们预留点)
     */
    public static final int MAX_LEN = 2000 ;

    /**
     * 环境名称
     */
    protected String envName;
    
    /**
     * 应用名称，比如 B端、C端、访客等
     */
    protected String appName;
    
    /**
     * 告警信息 key-value
     */
    protected Map<String, String> alarmInfo;
    
    protected Map.Entry<String, String> jumpUrl;
    
    public AlarmObject() {
        alarmInfo  = new LinkedHashMap<>();
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    /**
     * <br>应用名称，比如 B端、C端、访客等
     *
     * @return
     * @author YellowTail
     * @since 2019-05-28
     */
    public String getAppName() {
        return appName;
    }

    /**
     * <br>应用名称，比如 B、C
     *
     * @param appName
     * @author YellowTail
     * @since 2019-05-28
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, String> getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(Map<String, String> alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    /**
     * <br>得到 业务名称
     *
     * @author YellowTail
     * @since 2019-05-20
     */
    protected abstract String getBusinessName() ;
    
    private String getAlarmMsg() {
        StringBuilder sb = new StringBuilder(256);
        
        sb.append(envName)
            .append(" ").append(getIp())
            .append(" ").append(appName)
            .append(" 实时新增告警")
            .append(" <font color=\"#FF1493\">").append(getBusinessName()).append("</font>，请相关同事注意。\n");
        
        if (null != alarmInfo) {
            alarmInfo.forEach( (k,v) -> {
                sb.append("> ").append(k).append(": <font color=\"comment\">").append(v).append("</font> \n\n");
            });
        }
        
        if (null != jumpUrl) {
            sb.append("> ").append(String.format("[%s](%s)", jumpUrl.getKey(), jumpUrl.getValue())).append("\n");
        }
        
        System.out.println(sb.toString());
        
        return sb.toString();
    }
    
    public RobotMessage getMessage() {
        RobotMessage robotMessage = new RobotMessage();
        
        MarkdownMessage markdownMessage = new MarkdownMessage();
        markdownMessage.setContent(getAlarmMsg());
        
        robotMessage.setMarkdown(markdownMessage);
        
        return robotMessage;
    }
    
    public String getIp() {
        String hostAddress = null;
        try {
            InetAddress thisIp = InetAddress.getLocalHost();
            hostAddress = thisIp.getHostAddress();
        } catch (Exception e) {
        }
        
        if (null == hostAddress) {
            hostAddress = "";
        }
        
        return hostAddress;
    }

    @Override
    public String toString() {
        return "AlarmObject [" + 
                ", envName=" + envName + 
                ", alarmInfo=" + alarmInfo + 
                ", businessName=" + getBusinessName() + 
                "]";
    }
    
    
}
