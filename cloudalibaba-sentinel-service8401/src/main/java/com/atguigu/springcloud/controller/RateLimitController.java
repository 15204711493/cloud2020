package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.MyHandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

//    @GetMapping("/byResource")
//    @SentinelResource(value = "byResource",blockHandler = "handleException")
//    public CommonResult byResource(){
//        return new CommonResult(200,"按资源名称限流测试ok",new Payment(2020L,"serial001"));
//    }
//
//    public CommonResult handleException(BlockException e){
//        return new CommonResult(444,e.getClass().getCanonicalName()+" 服务不可用");
//    }
//
//    @GetMapping("/buUrl")
//    @SentinelResource(value = "buUrl")
//    public CommonResult byUrl(){
//        return new CommonResult(200,"安装url限流测试ok",new Payment(2020L,"serial001"));
//    }


    @GetMapping("/cc/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerE")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"自定义限流测试ok",new Payment(2020L,"serial001"));
    }





}
