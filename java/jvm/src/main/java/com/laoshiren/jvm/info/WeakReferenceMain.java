package com.laoshiren.jvm.info;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @ClassName WeakReferenceMain
 * @Description
 * @Author laoshiren
 * @Date 16:26 2022/11/1
 */
public class WeakReferenceMain {

    public static void main(String[] args) {
        Integer a = new Integer(1);
        Integer a1 = new Integer(1);

        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        HashMap<Integer, String> hashMap = new HashMap<>();
        weakHashMap.put(a, "yyds");
        hashMap.put(a1, "yyds");
        System.out.println(weakHashMap);
        System.out.println(hashMap);

        a = null;
        a1 = null;
        System.gc();

        System.out.println(weakHashMap);
        System.out.println(hashMap);
    }
}
