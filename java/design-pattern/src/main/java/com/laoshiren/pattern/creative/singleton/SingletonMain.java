package com.laoshiren.pattern.creative.singleton;

/**
 * @ClassName SingltonMain
 * @Description
 * @Author laoshiren
 * @Date 20:10 2022/9/8
 */
public class SingletonMain {

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
    }

}



class Singleton {
    private Singleton() {}

    //由静态内部类持有单例对象，但是根据类加载特性，我们仅使用Singleton类时，不会对静态内部类进行初始化
    private static class Holder {
        private final static Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){   //只有真正使用内部类时，才会进行类初始化
        return Holder.INSTANCE;   //直接获取内部类中的
    }
}
