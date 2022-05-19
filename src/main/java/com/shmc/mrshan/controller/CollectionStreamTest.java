package com.shmc.mrshan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionStreamTest {
    public static void main(String[] args) {
        User user1 = new User(12,"小红",100D);
        User user2 = new User(22,"小明",200D);
        User user3 = new User(32,"小亮",300D);
        User user4 = new User(42,"小刚",400D);
        User user5 = new User(52,"小刚",500D);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);

        List<User> userList = list.stream().filter(x -> x.getAge() > 20).collect(Collectors.toList());
        userList.forEach(System.out::print);

        System.out.println();
        System.out.println("--------------------------------------");
        List<String> collect = list.stream().map(x -> x.getName()+" ").collect(Collectors.toList());
        collect.forEach(System.out::print);

        System.out.println();
        System.out.println("--------------------------------------");
        Double aDouble = list.stream().map(x -> x.getMoney()).max(Double::compareTo).get();
        System.out.println(aDouble);

        System.out.println();
        System.out.println("--------------------------------------");
        Double collect1 = list.stream().map(x -> x.getMoney()).collect(Collectors.summingDouble(x -> x*10));
        System.out.println(collect1);

        System.out.println();
        System.out.println("--------------------------------------");
        Map<String, Map<Integer, List<User>>> collect2 = list.stream().collect(Collectors.groupingBy(x -> x.getName(), Collectors.groupingBy(x -> x.getAge())));
        System.out.println(collect2);

        Map<String, Map<Integer, Map<Double, List<User>>>> collect3 = list.stream().collect(Collectors.groupingBy(x -> x.getName(), Collectors.groupingBy(x -> x.getAge(), Collectors.groupingBy(x -> x.getMoney()))));
        System.out.println(collect3);
    }
}
