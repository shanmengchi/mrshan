package com.shmc.mrshan.utils;

/**
 * 作者 : 向哲-mac
 * 时间 : 2021/4/7 18:27
 * <--  -->
 * 人生犹如一首歌，音调高低起伏，旋律抑扬顿挫；
 * 人生仿佛一本书，写满了酸甜苦辣，记录着喜怒哀乐；
 * 人生又像一局棋，布满了层层危险，也撒遍了道道机遇；
 * 人生恰是一条路，有水穷山尽的坎坷，也有柳暗花明的坦途。
 * 愿友成败一笑过，潇洒向前行！
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean
 */
@Component
public class BeanUtils implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Spring上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


}
