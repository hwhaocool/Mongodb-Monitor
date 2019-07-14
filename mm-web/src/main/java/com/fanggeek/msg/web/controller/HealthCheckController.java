package com.fanggeek.msg.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.fanggeek.mm.model.vo.HealthInfo;

/**
 * <br>健康检查接口
 *
 * @author YellowTail
 * @since 2019-07-12
 */
@RequestMapping("/")
@RestController
public class HealthCheckController {
    
    @ApolloConfig
    private Config config;
    
    //健康检查接口
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public HealthInfo info( HttpServletRequest request) {
        
        HealthInfo info = new HealthInfo();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        TimeZone e8 = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(e8);

        info.setTime(sdf.format(new Date()));
        
        info.setTip(config.getProperty("test", "default"));

        return info;
    }
}
