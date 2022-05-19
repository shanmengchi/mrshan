package com.shmc.mrshan.controller;

public class User{
    private Integer age;
    private String name;
    private Double money;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public User(Integer age, String name, Double money) {
        this.age = age;
        this.name = name;
        this.money = money;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }


}
