package com.shmc.mrshan.controller;

import com.alibaba.fastjson.JSONObject;
import com.shmc.mrshan.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class TestController {
    @Autowired
    TestService testService;
    @RequestMapping("/test1")
    public Map test1(){
        return  new HashMap<String,String>(){
            {
                put("小明","28");
                put("小红","23");
            }
        };
    }
    @RequestMapping("/test2")
    public JSONObject test2() throws ExecutionException, InterruptedException {
        return  testService.test1();
    }
}
