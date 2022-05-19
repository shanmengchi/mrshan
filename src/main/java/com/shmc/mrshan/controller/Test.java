package com.shmc.mrshan.controller;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        User user1 = new User(12,"小红",100D);
        User user2 = new User(22,"小明",200D);
        User user3 = new User(32,"小亮",300D);
        User user5 = new User(52,"小刚",500D);
        User user4 = new User(42,"小刚",400D);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user5);
        list.add(user4);
        System.out.println(list);
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getAge()-o1.getAge();
            }
        });
        System.out.println("-------------------------------------------------------");
        list.add(user4);
        System.out.println("-------------------------------------------------------");


        System.out.println(list);

        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getAge()-o1.getAge();
            }
        });

        System.out.println("-------------------------------------------------------");


        System.out.println(list);

    }
}
