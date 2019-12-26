package com.github.hwhaocool.mm.common.constants;

/**
 * 常量类（不好归类的常量）
 * @Author: AndrewYan
 * @Date: 2019/6/26 15:54
 */
public class Constants {
    public static final Integer INTEGER_1  = new Integer(1);

    public final static String ROLE_TYPE_AGENT = "AGENT";
    
    public static int DEFAULT_MONGO_CONNECTION_PER_HOST = 10;
    
    public class DBName {
        public static final String SYSTEM_PROFILE = "system.profile";
        public static final String SLOW_OP_RECORD = "slow_op_record";
    }
    
    public class Env {
        public static final String HOST = "details-host";                    //域名
        public static final String COST = "min-cost";                        // 耗时 最小值
        public static final String MAX_DOCS = "max-docs";                    // 扫描文档数 最大值
    }
}
