package com.shmc.mrshan.controller;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(){
            {
              add("1") ;
              add("2") ;
              add("3") ;
              add("4") ;
              add("5") ;
              add("6") ;
              add("7") ;
              add("8") ;
              add("9") ;
              add("10") ;
              add("11") ;
            }
        };
        List<List<String>> result = new ArrayList<>();
        for (int i = 0;i<=list.size()/5;i++) {
            List<String> list1 = new ArrayList<>();
            list1.addAll(list.subList(i*5,((i+1)*5) > list.size() ? list.size():(i+1)*5));

            result.add(list1);
        }
        System.out.println(result);
    }
}
