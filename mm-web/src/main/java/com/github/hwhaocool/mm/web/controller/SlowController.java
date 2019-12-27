package com.github.hwhaocool.mm.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.hwhaocool.mm.service.SlowOpService;


/**
 * <br>慢查询相关
 *
 * @author YellowTail
 * @since 2019-07-12
 */
@RequestMapping("/api/mm/slow/")
@RestController
public class SlowController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SlowController.class);
    
    @Autowired
    private SlowOpService slowService;
    
    //健康检查接口
    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String details(@RequestParam(value = "id", required = false) final String id) {
        
        LOGGER.info("SlowController details, id {}", id);
        
        return slowService.queryById(id);
    }
}
