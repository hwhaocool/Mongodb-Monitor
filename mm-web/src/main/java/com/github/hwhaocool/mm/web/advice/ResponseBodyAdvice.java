package com.github.hwhaocool.mm.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理入口
 * @Author: AndrewYan
 * @Date: 2019/6/26 16:12
 */
@RestControllerAdvice
public class ResponseBodyAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBodyAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

//    /**
//     * 全局异常捕捉处理
//     * @param ex
//     * @return
//     */
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public CommonResultVO errorHandler(Exception ex) {
//        LOGGER.warn("ResponseBodyAdvice errorHandler occurred Exception, ", ex);
//        CommonResultVO resultVO = new CommonResultVO();
//        resultVO.setErrorCode(10000);
//        resultVO.setErrorMsg("消息微服务-服务器错误，请稍后重试");
//        return resultVO;
//    }
//
//    @ResponseBody
//    @ExceptionHandler(value = BizException.class)
//    public CommonResultVO businessExceptionHandler(BizException ex) {
//        LOGGER.warn("ResponseBodyAdvice businessExceptionHandler occurred Exception, ", ex);
//        CommonResultVO resultVO = new CommonResultVO();
//        resultVO.setErrorCode(ex.getCode());
//        resultVO.setErrorMsg(ex.getMsg());
//        return resultVO;
//    }
    
    
}
