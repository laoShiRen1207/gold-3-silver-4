package com.laoshiren.pattern.structural.appearance;

/**
 * @ClassName FacadeModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:17 2022/9/8
 */
public class FacadeModeMain {

    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.marry();
    }

}

class SubSystemA {
    public void test1(){
        System.out.println("排队");
    }
}

class SubSystemB {
    public void test1(){
        System.out.println("领证");
    }
}

class SubSystemC {
    public void test1(){
        System.out.println("结婚");
    }
}

class Facade {

    SubSystemA a = new SubSystemA();
    SubSystemB b = new SubSystemB();
    SubSystemC c = new SubSystemC();

    public void marry(){   //红白喜事一条龙服务
        a.test1();
        b.test1();
        c.test1();
    }
}