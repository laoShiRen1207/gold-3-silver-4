package com.laoshiren.base.collect.iterator;

import java.util.Iterator;
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

        //生成一个新的迭代器
        Iterator<String> iterator = linkedList.iterator();
        //判断是否还有下一个元素
        while (iterator.hasNext()){
            //获取下一个元素（获取一个少一个）
            String i = iterator.next();
            System.out.println(i);
        }
    }

}
