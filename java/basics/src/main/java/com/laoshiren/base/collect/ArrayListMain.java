package com.laoshiren.base.collect;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListMain
 * @Description
 * @Author laoshiren
 * @Date 14:14 2022/9/1
 */
public class ArrayListMain {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        String s = list.get(0);
        // rangeCheck(index);
        // 检查index 是否超过 list size的 超过抛出越界的异常
        list.set(1,"2");
        // 也是先范围检查
        // 获取老的元素
        // 元素覆盖
        // 将老元素返回
        list.add("3");
        // 首先确保容量够用
        // 然后赋值到数组对应的 size++的位置
    }
}
