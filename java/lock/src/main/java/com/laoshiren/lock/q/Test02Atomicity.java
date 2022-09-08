package com.laoshiren.lock.q;

import java.util.ArrayList;

/**
 * @ClassName Test02Atomicity
 * @Description
 * @Author laoshiren
 * @Date 15:35 2022/9/2
 */
public class Test02Atomicity {

    private static int number = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable increment = () -> {
            for (int i = 0; i < 1000; i++) {
                number++;
            }
        };
        ArrayList<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread t = new Thread(increment);
            t.start();
            ts.add(t);
        }
        for (Thread t : ts) {
            t.join();
        }
        System.out.println("number = " + number);
    }

}
