package com.laoshiren.base.box;

/**
 * @ClassName Boxed
 * @Description
 * @Author laoshiren
 * @Date 11:16 2022/9/1
 */
public class Boxed {

    public static void main(String[] args) {
        //包装类型可以直接接收对应类型的数据，并变为一个对象！
        Integer i = 1;
        //包装类型可以直接被当做一个基本类型进行操作！
        System.out.println(i + i);
        //注意，Java为了优化，有一个缓存机制，如果是在-128~127之间的数，会直接使用已经缓存好的对象，而不是再去创建新的！
        Integer valueOf = Integer.valueOf(1);


        Integer i1 = 129;
        Integer i2 = 129;
        //实际上判断是两个对象是否为同一个对象（内存地址是否相同）
        System.out.println(i1 == i2);
        //这个才是真正的值判断！
        System.out.println(i1.equals(i2));
        System.out.println();
        q();
    }


    public static void q() {
        Integer i1 = 28912;
        Integer i2 = 28912;
        Integer i3 = Integer.valueOf(28913);
        System.out.println(i1 == i2);
        System.out.println(  i3==i1+1);
    }

}
