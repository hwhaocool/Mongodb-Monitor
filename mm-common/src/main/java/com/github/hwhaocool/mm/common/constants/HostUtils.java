package com.github.hwhaocool.mm.common.constants;

import java.util.Map;

public class HostUtils {
    
    private  static String detailHost;
    
    static {
        Map<String, String> getenv = System.getenv();
        
        detailHost = getenv.getOrDefault(Constants.Env.HOST, null);
    }
    
    /**
     * <br>得到查询详情的域名
     *
     * @return
     * @author YellowTail
     * @since 2019-12-26
     */
    public static String getHost() {
        return detailHost;
    }

}
