package com.laoshiren.jvm.info;

/**
 * @ClassName Main
 * @Description
 * @Author laoshiren
 * @Date 13:17 2022/10/31
 */
public class Main {


    public static void main(String[] args) {
        Test a = new Test();
        Test b = new Test();

        a.another = b;
        b.another = a;

        //这里直接把a和b赋值为null，这样前面的两个对象我们不可能再得到了
        a = b = null;

        System.out.println(a);
        System.out.println(b);
    }

    private static class Test {
        Test another;
    }

}
