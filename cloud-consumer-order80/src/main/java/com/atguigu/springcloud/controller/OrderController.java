package com.atguigu.springcloud.controller;

import com.atguigu.myrule.MyselfRule;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;


    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);

    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }


    @GetMapping("/consumer/payment/postForEntity/create")
    public CommonResult<Payment> create2(Payment payment){
        ResponseEntity<CommonResult> resultResponseEntity =
                restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
        if(resultResponseEntity.getStatusCode().is2xxSuccessful()){
            return resultResponseEntity.getBody();
        }else {
            return new CommonResult<>(444,"添加失败");
        }
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable Long id){
       ResponseEntity<CommonResult>  resultResponseEntity=
               restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
       if(resultResponseEntity.getStatusCode().is2xxSuccessful()){
           log.info("===================================");
           log.info(resultResponseEntity.getStatusCode()+"\t"+resultResponseEntity.getHeaders());
           return resultResponseEntity.getBody();
       }else {
           return new CommonResult<>(444,"操作失败");
       }
    }

    @GetMapping("/consumer/payment/lb")
    public String paymentLB(){

        List<ServiceInstance> serviceInstances =
                discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(serviceInstances == null || serviceInstances.size() <= 0){
            return null;
        }
        ServiceInstance service = loadBalancer.service(serviceInstances);
        URI uri = service.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);

    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin",String.class);
    }

}
