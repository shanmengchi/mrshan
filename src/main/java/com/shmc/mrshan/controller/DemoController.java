package com.shmc.mrshan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apple")
public class DemoController {
    @RequestMapping("/queryById")
    public String queryById(@RequestParam(name = "id", required = true) String id) {
        if("1".equals(id)){
            return "hello，mrshan";
        }
        return "没有数据";
    }
}