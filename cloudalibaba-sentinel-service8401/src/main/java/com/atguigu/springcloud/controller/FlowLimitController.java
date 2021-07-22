package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {

    @GetMapping("/a")
    public String a(){

//        try {
//            TimeUnit.MILLISECONDS.sleep(800);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "a";
    }

    @GetMapping("/b")
    public String b(){
        return "b";
    }


    @GetMapping("/d")
    public String d(){

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        int i =10/0;
        return "d";
    }


    @GetMapping("/p")
    @SentinelResource(value = "p",blockHandler = "deal_p")
    public String testH(@RequestParam(value = "p1",required = false) String p1,
                        @RequestParam(value = "p2",required = false) String p2){
        return "error";
    }

    public String deal_p(String p1, String p2, BlockException blockE){
        return "dea_p error";
    }
}
