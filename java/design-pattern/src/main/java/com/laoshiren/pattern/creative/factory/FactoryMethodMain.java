package com.laoshiren.pattern.creative.factory;

/**
 * @ClassName FactoryMethodMain
 * @Description
 * @Author laoshiren
 * @Date 20:08 2022/9/8
 */
public class FactoryMethodMain {

    /**
     * 水果抽象类
     */
    static abstract class Fruit {
        private final String name;

        public Fruit(String name) {
            this.name = name;
        }

        //打印一下当前水果名称，还有对象的hashCode
        @Override
        public String toString() {
            return name + "@" + hashCode();
        }
    }

    /**
     * 苹果，继承自水果
     */
    static class Apple extends Fruit {
        public Apple() {
            super("苹果");
        }
    }

    /**
     * 橘子，也是继承自水果
     */
    static class Orange extends Fruit {
        public Orange() {
            super("橘子");
        }
    }


    static class FruitFactory {
        public static <T extends Fruit> T getFruit(String type) {
            switch (type) {
                case "苹果":
                    return (T) new Apple();
                case "橘子":
                    return (T) new Orange();
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        Fruit apple = FruitFactory.getFruit("苹果");
        System.out.println(apple);
    }

}
