package com.laoshiren.pattern.creative.abstractFactory;

/**
 * @ClassName AbstractFactoryMain
 * @Description
 * @Author laoshiren
 * @Date 20:09 2022/9/8
 */
public class AbstractFactoryMain {

    public static class Router {
    }

    public static class Table {
    }

    public static class Phone {
    }

    public static abstract class AbstractFactory {
        public abstract Phone getPhone();
        public abstract Table getTable();
        public abstract Router getRouter();
    }

    public static void main(String[] args) {

    }
}
