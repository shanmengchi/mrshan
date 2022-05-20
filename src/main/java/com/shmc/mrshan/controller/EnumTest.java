package com.shmc.mrshan.controller;

public enum EnumTest implements TestInterface{
    TEST1("200","OK") {
        @Override
        public void say(String message) {
            System.out.println(EnumTest.TEST1.code+"         "+this.name()+"         "+message);
        }
    },
    TEST2("300","重回向") {
        @Override
        public void say(String message) {
            System.out.println(EnumTest.TEST2.code+"         "+this.name()+"         "+message);
        }
    },
    TEST3("400","请求错误"){
        @Override
        public void say(String message) {
            System.out.println(EnumTest.TEST3.code+"         "+this.name()+"         "+message);
        }
    },

    ;

    private String code;
    private String message;

    EnumTest(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static void main(String[] args) {
//        System.out.println(EnumTest.TEST1.message);
        EnumTest[] values = EnumTest.values();
        for (EnumTest enumTest : values) {
            enumTest.say("WOW!!!"+enumTest.message);
        }
    }
}
