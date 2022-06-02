package com.shmc.mrshan.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    @Async(value = "asyncExecutor")
    public Future<JSONObject> asyncTest1(List<Integer> list,int i) {
        JSONObject resultObj = new JSONObject();
        try{
            if(i%2!=0){
                throw new RuntimeException("奇数报错");
            }
            resultObj.put("code","success");
            resultObj.put("data",list.size());
        }catch (Exception e){
            resultObj.put("code","error");
            resultObj.put("data","第"+i*10+"-"+(i+1)*10+"行失败");
            throw  new RuntimeException(e);
        }
        return new AsyncResult<>(resultObj);
    }

    @Async(value = "asyncExecutor")
    public Future<Integer> asyncTest2() {
        int i = 1/0;
        return new AsyncResult<>(0);
    }
}
