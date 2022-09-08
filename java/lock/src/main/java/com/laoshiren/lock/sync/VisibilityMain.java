package com.laoshiren.lock.sync;

/**
 * @ClassName VisibilityMain
 * @Description
 * @Author laoshiren
 * @Date 16:54 2022/9/5
 */
public class VisibilityMain {
    private static final Object o = new Object();

    private static boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (run) {
                synchronized (o) {

                }
            }
        });
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(() -> {
            run = false;
            System.out.println("时间到，线程2设置为false");
        });
        t2.start();
    }

}
