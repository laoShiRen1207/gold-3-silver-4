package com.laoshiren.base.collect;

import java.util.LinkedList;

/**
 * @ClassName IteratorMain
 * @Description
 * @Author laoshiren
 * @Date 15:34 2022/9/1
 */
public class IteratorMain {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<String>() {
            {
                this.add("ArrayList");
                this.add("LinkedList");
            }
        };

        for (String s : linkedList) {
            System.out.println(s);
        }
    }

}
