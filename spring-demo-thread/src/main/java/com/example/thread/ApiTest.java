package com.example.thread;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/15
 * @Time: 11:18
 * @Description: No Description
 */
@RestController
@RequestMapping("/api")
public class ApiTest {

    @RequestMapping("/hehe")
    public String say() {
        throw new NullPointerException();
    }

}
