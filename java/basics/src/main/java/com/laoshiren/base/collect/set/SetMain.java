package com.laoshiren.base.collect.set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * @ClassName SetMain
 * @Description
 * @Author laoshiren
 * @Date 16:24 2022/9/1
 */
public class SetMain {

    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.forEach(System.out::println);

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();  //会自动保存我们的插入顺序
        linkedHashSet.add(120);
        linkedHashSet.add(11);
        linkedHashSet.add(13);
        for (Integer integer : linkedHashSet) {
            System.out.println(integer);
        }

        TreeSet<Integer> set = new TreeSet<>((a, b) -> a - b);   //在创建对象时指定规则即可
        set.add(1);
        set.add(3);
        set.add(2);
        System.out.println(set);
    }
}
