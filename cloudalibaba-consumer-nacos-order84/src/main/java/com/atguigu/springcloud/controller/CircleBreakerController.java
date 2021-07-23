package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentNacosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

    @Value("${service-url.nacos-user-service}")
    private String SERVICE_URL;

    @Resource
    private RestTemplate restTemplate;


    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback")
    //@SentinelResource(value = "fallback",fallback ="handlerFallback" )
    @SentinelResource(value = "fallback",blockHandler = "blockHandler") //sentinel 图像管理界面配置
    public CommonResult<Payment> fallback(@PathVariable Long id){
        CommonResult<Payment> result =
                restTemplate.getForObject(SERVICE_URL+"/payment/"+id,CommonResult.class,id);
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException:"+"非法参数异常");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException:"+"空指针异常");

        }
        return result;
    }

//    public CommonResult<Payment> handlerFallback(@PathVariable Long id,Throwable e){
//        Payment payment = new Payment(id,null);
//        return new CommonResult<>(444,"兜底异常"+e.getMessage(),payment);
//    }

    public CommonResult<Payment> blockHandler(@PathVariable Long id, BlockException e){
        Payment payment = new Payment(id,null);
        return new CommonResult<>(445,"blockException-sentinel"+e.getMessage(),payment);
    }


    @Resource
    private PaymentNacosService paymentService;

    @GetMapping("/consumer/payment/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
       return  paymentService.paymentSQL(id);
    }
}
