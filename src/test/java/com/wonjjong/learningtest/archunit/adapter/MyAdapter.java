package com.wonjjong.learningtest.archunit.adapter;

import com.wonjjong.learningtest.archunit.application.MyService;

public class MyAdapter {
    MyService myService;

    void run() {
        myService = new MyService();
        System.out.println(myService);
    }
}
