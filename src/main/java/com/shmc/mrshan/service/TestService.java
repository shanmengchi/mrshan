package com.shmc.mrshan.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class TestService {
    @Autowired
    AsyncService asyncService;
    public JSONObject test1() throws ExecutionException, InterruptedException {

        Future<Integer> integerFuture = asyncService.asyncTest2();
        Integer integer = integerFuture.get();
        JSONObject jsonObject = new JSONObject();

        return  jsonObject;
    }
    public JSONObject test2() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        int m = 0;
        while(true){
            m++;
            list.add(m);
            if(m==1000){
               break;
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0;i<=list.size()/10;i++) {
            List<Integer> list1 = new ArrayList<>();
            list1.addAll(list.subList(i*10,((i+1)*10) > list.size() ? list.size():(i+1)*10));
            if(list1==null || list1.size()==0){
                continue;
            }
            result.add(list1);
        }

        int sum = 0;
        List<Future<JSONObject>> listF = new ArrayList<>();
        for (int i = 0;i<result.size();i++) {
            Future<JSONObject> integerFuture = asyncService.asyncTest1(result.get(i),i);
            listF.add(integerFuture);
//            sum+=i;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray errorArr = new JSONArray();
        for (Future<JSONObject> future : listF) {
            JSONObject resultObj = future.get();
            if("success".equals(resultObj.getString("code"))){
                sum+=resultObj.getInteger("data");
            }else{
                errorArr.add(resultObj.getString("data"));
            }
        }
        jsonObject.put("累计成功",sum);
        jsonObject.put("失败详情",errorArr);

        return  jsonObject;
    }
}
