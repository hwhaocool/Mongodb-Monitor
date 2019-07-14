package com.fanggeek.msg.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fanggeek.mm.model.vo.SlowList;


/**
 * <br>慢查询相关
 *
 * @author YellowTail
 * @since 2019-07-12
 */
@RequestMapping("/v1/odin/mongo/slow/")
@RestController
public class SlowController {
    
    //健康检查接口
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public SlowList info(
            HttpServletRequest request) {
        
        SlowList list = new SlowList();


        return list;
    }
}
