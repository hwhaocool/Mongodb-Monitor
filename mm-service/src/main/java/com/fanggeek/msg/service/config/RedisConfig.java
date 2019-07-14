package com.fanggeek.msg.service.config;

import com.fanggeek.base.service.redis.RedisAbstractService;
import com.fanggeek.base.service.redis.RedisUrlManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "common")
public class RedisConfig {
    
    private String    envName;            //环境信息
    
    
    /**
     * <br>得到环境信息
     *
     * @return
     * @author YellowTail
     * @since 2019-06-06
     */
    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    @Bean(name="agentRedisService")
    public RedisAbstractService agentRedisService() {
        return RedisUrlManager.getAgentRedis(envName);
    }
    
    @Bean(name="userRedisService")
    public RedisAbstractService userRedisService() {
        return RedisUrlManager.getUserRedis(envName);
    }
        

}
