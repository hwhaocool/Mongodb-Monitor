package com.github.hwhaocool.mm.service.threshold;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>阈值： 何时该告警
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class AlarmThreshold {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmThreshold.class);
    
    private  static int docsExaminedThreshold = 10000;                    //扫描文档数的阈值，默认 10万
    
    static {
        Map<String, String> getenv = System.getenv();
        
        String str = getenv.getOrDefault("max-docs", "1000");
        
        try {
            docsExaminedThreshold = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            LOGGER.info("env max-docs {} is invalid number format ", str);
        }
        
    }
    
    /**
     * <br> 扫描文档数的阈值
     *
     * @return
     * @author YellowTail
     * @since 2019-12-25
     */
    public static int getDocsThreshold() {
        return docsExaminedThreshold;
    }

}
