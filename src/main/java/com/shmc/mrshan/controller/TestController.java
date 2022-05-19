package com.shmc.mrshan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @RequestMapping("/test1")
    public Map test1(){
        return  new HashMap<String,String>(){
            {
                put("小明","28");
                put("小红","23");
            }
        };
    }
}
