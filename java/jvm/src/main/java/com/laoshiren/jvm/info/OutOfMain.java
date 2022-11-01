package com.laoshiren.jvm.info;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OutOfMain
 * @Description
 * @Author laoshiren
 * @Date 14:36 2022/10/26
 */
public class OutOfMain {

    public static void main(String[] args) {
        List<Test> list = new ArrayList<>();
        while (true) {
            list.add(new Test());    //无限创建Test对象并丢进List中
        }
    }

    static class Test {
    }

}
