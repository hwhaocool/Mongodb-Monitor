package com.fanggeek.mm.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fanggeek.mm.model.vo.SlowList;
import com.fanggeek.mm.service.SlowOpService;


/**
 * <br>慢查询相关
 *
 * @author YellowTail
 * @since 2019-07-12
 */
@RequestMapping("/v1/odin/mongo/slow/")
@RestController
public class SlowController {
    
    @Autowired
    private SlowOpService slowService;
    
    //健康检查接口
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public SlowList info2() {
        slowService.test();
        
        return new SlowList();
    }
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public SlowList info(
            HttpServletRequest request) {
        
        SlowList list = new SlowList();
        
        slowService.test();

        return list;
    }
}
