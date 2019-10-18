package com.example.thread.example.people;

import com.example.thread.entity.People;
import com.example.thread.entity.Teacher;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/12
 * @Time: 14:08
 * @Description: No Description
 */
public class TeacherExample {

    public static void main(String[] args) {
        People teacher = new Teacher();
        teacher.speak();
        int a = 0;
        if (a== 0)
            System.out.println(1);
    }


}
