package com.example.thread.entity;

import java.io.Serializable;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/8
 * @Time: 10:22
 * @Description: No Description
 */
public class Product implements Serializable {

    private static final long serialVersionUID = -1902916417368097826L;

    int id;
    int clickCount;

    public int getClickCount() {
        return clickCount;
    }

    public Product setClickCount(int clickCount) {
        this.clickCount = clickCount;
        return this;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }
}
