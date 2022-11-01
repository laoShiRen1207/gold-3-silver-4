package com.laoshiren.jvm.info;

/**
 * @ClassName Math
 * @Description
 * @Author laoshiren
 * @Date 10:24 2022/10/25
 */
public class Math {



    public int test(){    //和上面的例子一样
        int a = 10;
        int b = 20;
        int c = a + b;
        return c;
    }

    public static void main(String[] args) {
        //不能直接写"abc"，双引号的形式，写了就直接在常量池里面吧abc创好了
        String str1 = new String("ab")+new String("c");
        System.out.println(str1.intern() == str1);
        String str2 = new String("ab")+new String("c");
        System.out.println(str1 == str1.intern());
        System.out.println(str2.intern() == str1);
    }
}
