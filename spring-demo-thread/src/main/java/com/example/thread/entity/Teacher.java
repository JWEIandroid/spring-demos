package com.example.thread.entity;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/12
 * @Time: 14:06
 * @Description: No Description
 */
public class Teacher implements People{

    @Override
    public void speak() {
        System.out.println("teacher speaking...");
    }
}
