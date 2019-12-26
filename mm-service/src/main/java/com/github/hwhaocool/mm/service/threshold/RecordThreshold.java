package com.github.hwhaocool.mm.service.threshold;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br> 阈值： 何时该 把慢日志记录下来供分析
 *
 * @author YellowTail
 * @since 2019-12-25
 */
public class RecordThreshold {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordThreshold.class);
    
    private  static int minCost = 1000;                               //耗时阈值,默认1000
    
    static {
        Map<String, String> getenv = System.getenv();
        
        String str = getenv.getOrDefault("min-cost", "1000");
        
        try {
            minCost = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            LOGGER.info("env min-cost {} is invalid number format ", str);
        }
    }
    
    /**
     * <br> 得到 耗时阈值， 单位：毫秒
     *
     * @return
     * @author YellowTail
     * @since 2019-12-25
     */
    public static int minCost() {
        return minCost;
    }
}
