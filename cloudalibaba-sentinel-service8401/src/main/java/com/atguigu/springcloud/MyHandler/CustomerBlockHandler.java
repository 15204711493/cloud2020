package com.atguigu.springcloud.MyHandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult handlerE(BlockException exception){
        return new CommonResult(4444,"全局自定义限流测试ok全局1111111111");

    }

    public static CommonResult handlerE2(BlockException exception){
        return new CommonResult(4444,"全局自定义限流测试ok全局222222222");

    }
}
