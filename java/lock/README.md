# lock

[toc]
---

## synchronized

### 并发编程中的三个问题

#### 可见性

##### 可见性概念

可见性（Visibility）：是指一个线程对共享变量进行修改，另一个先立即得到修改后的最新值。

##### 可见性演示

案例演示：一个线程根据boolean类型的标记flag， while循环，另一个线程改变这个flag变量的值，另
一个线程并不会停止循环。
小结

```java
package com.itheima.concurrent_problem;

/**
 案例演示:
 一个线程对共享变量的修改,另一个线程不能立即得到最新值
 */
public class Test01Visibility {
    // 多个线程都会访问的数据，我们称为线程的共享数据
    private static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (run) {
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
```

并发编程时，会出现可见性问题，当一个线程对共享变量进行了修改，另外的线程并没有立即看到修改
后的最新值。

#### 原子性

##### 原子性概念

原子性（Atomicity）：在一次或多次操作中，要么所有的操作都执行并且不会受其他因素干扰而中
断，要么所有的操作都不执行。

##### 原子性演示

案例演示:5个线程各执行1000次 i++;

```java
package com.itheima.demo01_concurrent_problem;

import java.util.ArrayList;

/**
 案例演示:5个线程各执行1000次 i++;
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
        for (int i = 0; i < 5; i++) {
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
```

使用javap反汇编class文件，得到下面的字节码指令：

其中，对于 number++ 而言（number 为静态变量），实际会产生如下的 JVM 字节码指令：

```c
9: getstatic #12 // Field number:I
12: iconst_1
13: iadd
14: putstatic #12 // Field number:I
```

由此可见number++是由多条语句组成，以上多条指令在一个线程的情况下是不会出问题的，但是在多 线程情况下就可能会出现问题。比如一个线程在执行13: iadd时，另一个线程又执行9: getstatic。会导
致两次number++，实际上只加了1。

#### 有序性

##### 有序性概念

有序性（Ordering）：是指程序中代码的执行顺序，Java在编译时和运行时会对代码进行优化，会导致 程序最终的执行顺序不一定就是我们编写代码时的顺序。

```java

@JCStressTest
@Outcome(id = {"1", "4"}, expect = Expect.ACCEPTABLE, desc = "ok")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "danger")
@State
public class OrderMain {


    int num = 0;
    boolean ready = false;

    // 线程一执行的代码
    @Actor
    public void actor1(I_Result r) {
        if (ready) {
            r.r1 = num + num;
        } else {
            r.r1 = 1;
        }
    }

    // 线程2执行的代码
    @Actor
    public void actor2(I_Result r) {
        num = 2;
        ready = true;
    }

}
```

### Java内存模型（JMM）

#### 计算机结构

#### java内存模型

Java Memory Molde (Java内存模型/JMM)
，千万不要和Java内存结构混淆关于“Java内存模型”的权威解释，请参考 https://download.oracle.com/otn-pub/jcp/memory_model1.0-pfd-spec-oth-JSpec/memory_model-1_0-pfd-spec.pdf。
Java内存模型，是Java虚拟机规范中所定义的一种内存模型，Java内存模型是标准化的，屏蔽掉了底层不同计算机的区别。
Java内存模型是一套规范，描述了Java程序中各种变量(线程共享变量)的访问规则，以及在JVM中将变量存储到内存和从内存中读取变量这样的底层细节，具体如下。

* 主内存
  主内存是所有线程都共享的，都能访问的。所有的共享变量都存储于主内存。
* 工作内存
  每一个线程有自己的工作内存，工作内存只存储该线程对共享变量的副本。线程对变量的所有的操 作(读，取)都必须在工作内存中完成，而不能直接读写主内存中的变量，不同线程之间也不能直接访问对方工作内存中的变量。

![image-20220905161333413.png](https://pic.img.ski/1662365736.png)

##### Java内存模型的作用

Java内存模型是一套在多线程读写共享数据时，对共享数据的可见性、有序性、和原子性的规则和保障。
synchronized,volatile

##### CPU缓存，内存与Java内存模型的关系

通过对前面的CPU硬件内存架构、Java内存模型以及Java多线程的实现原理的了解，我们应该已经意识到，多线程的执行最终都会映射到硬件处理器上进行执行。

但Java内存模型和硬件内存架构并不完全一致。对于硬件内存来说只有寄存器、缓存内存、主内存的概
念，并没有工作内存和主内存之分，也就是说Java内存模型对内存的划分对硬件内存并没有任何影响，因为JMM只是一种抽象的概念，是一组规则，不管是工作内存的数据还是主内存的数据，对于计算机硬件来说都会存储在计算机主内存中，当然也有可能存储到CPU缓存或者寄存器中，因此总体上来说，Java内存模型和计算机硬件内存架构是一个相互交叉的关系，是一种抽象概念划分与真实物理硬件的交叉。

JMM内存模型与CPU硬件内存架构的关系：

![image-20220905161634229.png](https://pic.img.ski/1662365822.png)

##### 小结

Java内存模型是一套规范，描述了Java程序中各种变量(线程共享变量)的访问规则，以及在JVM中将变量
存储到内存和从内存中读取变量这样的底层细节，Java内存模型是对共享数据的可见性、有序性、和原
子性的规则和保障。

#### 主内存与工作内存之间的交互

![image-20220905163709238.png](https://pic.img.ski/1662367045.png)

##### 小结

主内存与工作内存之间的数据交互过程

`lock -> read -> load -> use -> assign -> store -> write -> unlock`

### synchronized保证三大特性

synchronized能够保证在同一时刻最多只有一个线程执行该段代码，以达到保证并发安全的效果。

```java
public class Main {
    static {
        synchronized (锁对象) {
            // 受保护资源;
        }
    }
}
```

#### synchronized与原子性

```java
/**
 案例演示:5个线程各执行1000次 i++;
 */
public class Test01Atomicity {
    private static int number = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable increment = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    synchronized (Test01Atomicity.class) {
                        number++;
                    }
                }
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


    {
        for (int i = 0; i < 1000; i++) {
            synchronized (Test01Atomicity.class) {
                number++;
            }
        }
    }
}
```

对number++;增加同步代码块后，保证同一时间只有一个线程操作number++;。就不会出现安全问题。

synchronized保证原子性的原理，synchronized保证只有一个线程拿到锁，能够进入同步代码块。

#### synchronized与可见性

一个线程根据boolean类型的标记flag， while循环，另一个线程改变这个flag变量的值，另 一个线程并不会停止循环。

```java
/**
 案例演示:
 一个线程根据boolean类型的标记flag， while循环，另一个线程改变这个flag变量的值，
 另一个线程并不会停止循环.
 */
public class Test01Visibility {
    // 多个线程都会访问的数据，我们称为线程的共享数据
    private static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (run) {
// 增加对象共享数据的打印，println是同步方法
                System.out.println("run = " + run);
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

```

![image-20220905163709238.png](https://pic.img.ski/1662367045.png)

synchronized保证可见性的原理，执行synchronized时，会对应`lock`原子操作会刷新工作内存中共享变量的值

#### synchronized与有序性

##### 为什么要重排序

为了提高程序的执行效率，编译器和CPU会对程序中代码进行重排序。

##### as-if-serial语义

as-if-serial语义的意思是：不管编译器和CPU如何重排序，必须保证在单线程情况下程序的结果是正确的。

以下数据有依赖关系，不能重排序。

写后读：

```java 
int a=1;
int b=a;
```

写后写：

```java 
int a=1;
int a=2;
```

读后写：

```java 
int a=1;
int b=a;
int a=2;
```

编译器和处理器不会对存在数据依赖关系的操作做重排序，因为这种重排序会改变执行结果。但是，如 果操作之间不存在数据依赖关系，这些操作就可能被编译器和处理器重排序。

```java 
int a=1;
int b=2;
int c=a+b;
```

##### 使用synchronized保证有序性

synchronized后，虽然进行了重排序，保证只有一个线程会进入同步代码块，也能保证有序性。

synchronized保证有序性的原理，我们加synchronized后，依然会发生重排序，只不过，我们有同步 代码块，可以保证只有一个线程执行同步代码中的代码。保证有序性

### synchronized的特性

#### 可重入特性

一个线程可以多次执行synchronized,重复获取同一把锁。

```java
public class Demo01 {
    public static void main(String[] args) {
        Runnable sellTicket = new Runnable() {
            @Override
            public void run() {
                synchronized (Demo01.class) {
                    System.out.println("我是run");
                    test01();
                }
            }

            public void test01() {
                synchronized (Demo01.class) {
                    System.out.println("我是test01");
                }
            }
        };
        new Thread(sellTicket).start();
        new Thread(sellTicket).start();
    }
}

```

synchronized的锁对象中有一个计数器（recursions变量）会记录线程获得几次锁

##### 可重入的好处

1. 可以避免死锁
2. 可以让我们更好的来封装代码

synchronized是可重入锁，内部锁对象中会有一个计数器记录线程获取几次锁啦，在执行完同步代码块 时，计数器的数量会-1，知道计数器的数量为0，就释放这个锁。

#### 不可中断特性

一个线程获得锁后，另一个线程想要获得锁，必须处于阻塞或等待状态，如果第一个线程不释放锁，第 二个线程会一直阻塞或等待，不可被中断。

```java
/*
目标:演示synchronized不可中断
1.定义一个Runnable
2.在Runnable定义同步代码块
3.先开启一个线程来执行同步代码块,保证不退出同步代码块
4.后开启一个线程来执行同步代码块(阻塞状态)
5.停止第二个线程
*/
public class Demo02_Uninterruptible {
    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
// 1.定义一个Runnable
        Runnable run = () -> {
// 2.在Runnable定义同步代码块
            synchronized (obj) {
                String name = Thread.currentThread().getName();
                System.out.println(name + "进入同步代码块");
// 保证不退出同步代码块
                try {
                    Thread.sleep(888888);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
// 3.先开启一个线程来执行同步代码块
        Thread t1 = new Thread(run);
        t1.start();
        Thread.sleep(1000);
// 4.后开启一个线程来执行同步代码块(阻塞状态)
        Thread t2 = new Thread(run);
        t2.start();
// 5.停止第二个线程
        System.out.println("停止线程前");
        t2.interrupt();
        System.out.println("停止线程后");
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
```

```java
/*
目标:演示Lock不可中断和可中断
*/
public class Demo03_Interruptible {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
// test01();
        test02();
    }

    // 演示Lock可中断
    public static void test02() throws InterruptedException {
        Runnable run = () -> {
            String name = Thread.currentThread().getName();
            boolean b = false;
            try {
                b = lock.tryLock(3, TimeUnit.SECONDS);
                if (b) {
                    System.out.println(name + "获得锁,进入锁执行");
                    Thread.sleep(88888);
                } else {
                    System.out.println(name + "在指定时间没有得到锁做其他操作");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (b) {
                    lock.unlock();
                    System.out.println(name + "释放锁");
                }
            }
        };
        Thread t1 = new Thread(run);
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(run);
        t2.start();
// System.out.println("停止t2线程前");
// t2.interrupt();
// System.out.println("停止t2线程后");
//
// Thread.sleep(1000);
// System.out.println(t1.getState());
// System.out.println(t2.getState());
    }

    // 演示Lock不可中断
    public static void test01() throws InterruptedException {
        Runnable run = () -> {
            String name = Thread.currentThread().getName();
            try {
                lock.lock();
                System.out.println(name + "获得锁,进入锁执行");
                Thread.sleep(88888);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(name + "释放锁");
            }
        };
        Thread t1 = new Thread(run);
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(run);
        t2.start();
        System.out.println("停止t2线程前");
        t2.interrupt();
        System.out.println("停止t2线程后");
        Thread.sleep(1000);
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
```

不可中断是指，当一个线程获得锁后，另一个线程一直处于阻塞或等待状态，前一个线程不释放锁，后 一个线程会一直阻塞或等待，不可被中断。

synchronized属于不可被中断

Lock的lock方法是不可中断的

Lock的tryLock方法是可中断的

### synchronized原理

#### javap 反汇编

##### monitorenter

```java
public class Demo01 {
    private static Object obj = new Object();

    public static void main(String[] args) {
        synchronized (obj) {
            System.out.println("1");
        }
    }

    public synchronized void test() {
        System.out.println("a");
    }
}
```

我们要看synchronized的原理，但是synchronized是一个关键字，看不到源码。我们可以将class文件进行反汇编。

```c
public static void main(java.lang.String[]);
descriptor: ([Ljava/lang/String;)V
flags: ACC_PUBLIC, ACC_STATIC
Code:
stack=2, locals=4, args_size=1
0: iconst_0
1: istore_1
2: getstatic #2 // Field obj:Ljava/lang/Object;
5: dup
6: astore_2
7: monitorenter
8: iinc 1, 1
11: aload_2
12: monitorexit
13: goto 21
16: astore_3
17: aload_2
18: monitorexit
19: aload_3
20: athrow
21: return
Exception table:
from to target type
8 13 16 any
16 19 16 any
LineNumberTable:
line 8: 0
line 9: 2
line 10: 8
line 11: 11
line 12: 21
LocalVariableTable:
Start Length Slot Name Signature
0 22 0 args [Ljava/lang/String;
2 20 1 number I
StackMapTable: number_of_entries = 2
frame_type = 255 /* full_frame */
offset_delta = 16
locals = [ class "[Ljava/lang/String;", int, class java/lang/Object ]
stack = [ class java/lang/Throwable ]
frame_type = 250 /* chop */
offset_delta = 4
public synchronized void test();
descriptor: ()V
flags: ACC_PUBLIC, ACC_SYNCHRONIZED
Code:
stack=2, locals=1, args_size=1
0: getstatic #3 // Field
java/lang/System.out:Ljava/io/PrintStream;
3: ldc #4 // String a
5: invokevirtual #5 // Method
java/io/PrintStream.println:(Ljava/lang/String;)V
8: return
LineNumberTable:
line 15: 0
line 16: 8
LocalVariableTable:
Start Length Slot Name Signature
0 9 0 this
Lcom/itheima/demo04_synchronized_monitor/Demo01;

```

![image-20220906164334938.png](https://pic.img.ski/1662453848.png)



> 每一个对象都会和一个监视器monitor关联。监视器被占用时会被锁住，其他线程无法来获 取该monitor。 当JVM执行某个线程的某个方法内部的monitorenter时，它会尝试去获取当前对象对应
> 的monitor的所有权。其过程如下：
>
> 1. 若monior的进入数为0，线程可以进入monitor，并将monitor的进入数置为1。当前线程成为 monitor的owner（所有者）
> 2. 若线程已拥有monitor的所有权，允许它重入monitor，则进入monitor的进入数加1
> 3. 若其他线程已经占有monitor的所有权，那么当前尝试获取monitor的所有权的线程会被阻塞，直到monitor的进入数变为0，才能重新尝试获取monitor的所有权。

synchronized的锁对象会关联一个monitor,这个monitor不是我们主动创建的,是JVM的线程执行到这个同步代码块,发现锁对象没有monitor就会创建monitor,monitor内部有两个重要的成员变量owner:
拥有这把锁的线程,recursions会记录线程拥有锁的次数,当一个线程拥有monitor后其他线程只能等待

##### monitorexit

> 1. 能执行monitorexit指令的线程一定是拥有当前对象的monitor的所有权的线程。
> 2. 执行monitorexit时会将monitor的进入数减1。当monitor的进入数减为0时，当前线程退出 monitor，不再拥有monitor的所有权，此时其他被这个monitor阻塞的线程可以尝试去获取这个
     monitor的所有权

monitorexit插入在方法结束处和异常处，JVM保证每个monitorenter必须有对应的monitorexit。

通过javap反汇编我们看到synchronized使用编程了monitorentor和monitorexit两个指令.每个锁对象 都会关联一个monitor(监视器,它才是真正的锁对象),它内部有两个重要的成员变量owner会保存获得锁
的线程,recursions会保存线程获得锁的次数,当执行到monitorexit时,recursions会-1,当计数器减到0时 这个线程就会释放锁。

#### 深入JVM源码

在HotSpot虚拟机中，monitor是由ObjectMonitor实现的。其源码是用c++来实现的，位于HotSpot虚 拟机源码ObjectMonitor.hpp文件中(
src/share/vm/runtime/objectMonitor.hpp)。ObjectMonitor主 要数据结构如下

```c++
ObjectMonitor() {
    _header = NULL;
    _count = 0;
    _waiters = 0，
    _recursions = 0; // 线程的重入次数
    _object = NULL; // 存储该monitor的对象
    _owner = NULL; // 标识拥有该monitor的线程
    _WaitSet = NULL; // 处于wait状态的线程，会被加入到_WaitSet
    _WaitSetLock = 0 ;
    _Responsible = NULL;
    _succ = NULL;
    _cxq = NULL; // 多线程竞争锁时的单向列表
    FreeNext = NULL;
    _EntryList = NULL; // 处于等待锁block状态的线程，会被加入到该列表
    _SpinFreq = 0;
    _SpinClock = 0;
    OwnerIsThread = 0;
}
```
1. _owner：初始时为NULL。当有线程占有该monitor时，owner标记为该线程的唯一标识。当线程释放monitor时，owner又恢复为NULL。owner是一个临界资源，JVM是通过CAS操作来保证其线程安全的。
2. _cxq：竞争队列，所有请求锁的线程首先会被放在这个队列中（单向链接）。_cxq是一个临界资源，JVM通过CAS原子指令来修改_cxq队列。修改前_cxq的旧值填入了node的next字段，_cxq指向新值（新线程）。因此_cxq是一个后进先出的stack（栈）。
3. _EntryList：_cxq队列中有资格成为候选资源的线程会被移动到该队列中。
4. _WaitSet：因为调用wait方法而被阻塞的线程会被放在该队列中。



每一个Java对象都可以与一个监视器monitor关联，我们可以把它理解成为一把锁，当一个线程想要执行一段被synchronized圈起来的同步方法或者代码块时，该线程得先获取到synchronized修饰的对象对应的monitor。

我们的Java代码里不会显示地去创造这么一个monitor对象，我们也无需创建，事实上可以这么理解：monitor并不是随着对象创建而创建的。我们是通过synchronized修饰符告诉JVM需要为我们的某个对象创建关联的monitor对象。每个线程都存在两个ObjectMonitor对象列表，分别为free和used列表。同时JVM中也维护着global locklist。当线程需要ObjectMonitor对象时，首先从线程自身的free表中申请，若存在则使用，若不存在则从global list中申请。

![image-20220907133131608.png](https://pic.img.ski/1662528735.png)

### JDK6 synchronized优化

#### synchronized锁升级过程

高效并发是从JDK 5到JDK 6的一个重要改进，HotSpot虛拟机开发团队在这个版本上花费了大量的精力去实现各种锁优化技术，包括偏向锁( Biased Locking )、轻量级锁( Lightweight Locking )和如适应性自旋(Adaptive Spinning)、锁消除( Lock Elimination)、锁粗化( Lock Coarsening )等，这些技术都是为了在线程之间更高效地共享数据，以及解决竞争问题，从而提高程序的执行效率。

> 无锁--》偏向锁--》轻量级锁–》重量级锁

#### Java对象的布局

在JVM中，对象在内存中的布局分为三块区域：对象头、实例数据和对齐填充。如下图所示：

![image-20220907140744981.png](https://pic.img.ski/1662530872.png)

![image-20220908103300846.png](https://pic.img.ski/1662604391.png)

```xml
<dependency>
	<groupId>org.openjdk.jol</groupId>
	<artifactId>jol-core</artifactId>
	<version>0.9</version>
</dependency>
```

#### 偏向锁

> 无锁 `>` 偏向锁

偏向锁的“偏”，就是偏心的“偏”、偏袒的“偏”，它的意思是这个锁会偏向于第一个获得它的线程，会在对 象头存储锁偏向的线程ID，以后该线程进入和退出同步块时只需要检查是否为偏向锁、锁标志位以及 ThreadID即可。

![image-20220908104226302.png](https://pic.img.ski/1662604972.png)

不过一旦出现多个线程竞争时必须撤销偏向锁，所以撤销偏向锁消耗的性能必须小于之前节省下来的 CAS原子操作的性能消耗，不然就得不偿失了。

`即只有一个线程进出同步代码块即偏向锁，一旦有多个线程同时访问就存在锁竞争就会升级为轻量级锁`

##### 偏向锁原理

当线程第一次访问同步块并获取锁时，

偏向锁处理流程如下：

> 1. 虚拟机将会把对象头中的标志位设为“01”，即偏向模式。 
> 2. 同时使用CAS操作把获取到这个锁的线程的ID记录在对象的Mark Word之中 ，如果CAS操作成功，持有偏向锁的线程以后每次进入这个锁相关的同步块时，虚拟机都可以不再进行任何同步操作，偏向锁的效率高。

持有偏向锁的线程以后每次进入这个锁相关的同步块时，虚拟机都可以不再进行任何同步操作，偏向锁的效率高

##### 偏向锁撤销

> 1. 偏向锁的撤销动作必须等待全局安全点
> 2.  暂停拥有偏向锁的线程，判断锁对象是否处于被锁定状态 
> 3.  撤销偏向锁，恢复到无锁（标志位为 01）或轻量级锁（标志位为 00）的状态

当锁对象第一次被线程获取的时候，虚拟机将会把对象头中的标志位设为“01”，即偏向模式。同时使用CAS操作把获取到这个锁的线程的ID记录在对象的Mark Word之中 ，如果CAS操作成功，持有偏向锁的线程以后每 次进入这个锁相关的同步块时，虚拟机都可以不再进行任何同步操作，偏向锁的效率高。

##### 好处

偏向锁是在只有一个线程执行同步块时进一步提高性能，适用于一个线程反复获得同一锁的情况。偏向锁可以提高带有同步但无竞争的程序性能。

它同样是一个带有效益权衡性质的优化，也就是说，它并不一定总是对程序运行有利，如果程序中大多数的锁总是被多个不同的线程访问比如线程池，那偏向模式就是多余的。

#### 轻量级锁

轻量级锁是JDK 6之中加入的新型锁机制，它名字中的“轻量级”是相对于使用monitor的传统锁而言的，因此传统的锁机制就称为“重量级”锁。首先需要强调一点的是，轻量级锁并不是用来代替重量级锁的。

引入轻量级锁的目的：在多线程交替执行同步块的情况下，尽量避免重量级锁引起的性能消耗，但是如果多个线程在同一时刻进入临界区，会导致轻量级锁膨胀升级重量级锁，所以轻量级锁的出现并非是要替代重量级锁。

##### 轻量级锁原理

当关闭偏向锁功能或者多个线程竞争偏向锁导致偏向锁升级为轻量级锁，则会尝试获取轻量级锁，其步骤如下： 获取锁

> 1. 判断当前对象是否处于无锁状态（hashcode、0、01），如果是，则JVM首先将在当前线程的栈帧中建立一个名为锁记录（Lock Record）的空间，用于存储锁对象目前的Mark Word的拷贝（官方把这份拷贝加了一个Displaced前缀，即Displaced Mark Word），将对象的Mark Word复制到栈帧中的Lock Record中，将Lock Reocrd中的owner指向当前对象。
> 2. JVM利用CAS操作尝试将对象的Mark Word更新为指向Lock Record的指针，如果成功表示竞争到锁，则将锁标志位变成00，执行同步操作。
> 3. 如果失败则判断当前对象的Mark Word是否指向当前线程的栈帧，如果是则表示当前线程已经持有当前对象的锁，则直接执行同步代码块；否则只能说明该锁对象已经被其他线程抢占了，这时轻量级锁需要膨胀为重量级锁，锁标志位变成10，后面等待的线程将会进入阻塞状态。



![image-20220908134224601.png](https://pic.img.ski/1662615769.png)

##### 轻量级锁的释放

轻量级锁的释放也是通过CAS操作来进行的，主要步骤如下：

> 1. 取出在获取轻量级锁保存在Displaced Mark Word中的数据。 
> 2. 用CAS操作将取出的数据替换当前对象的Mark Word中，如果成功，则说明释放锁成功。
> 3. 如果CAS操作替换失败，说明有其他线程尝试获取该锁，则需要将轻量级锁需要膨胀升级为重量级锁。

对于轻量级锁，其性能提升的依据是“对于绝大部分的锁，在整个生命周期内都是不会存在竞争的”，如果打破这个依据则除了互斥的开销外，还有额外的CAS操作，因此在有多线程竞争的情况下，轻量级锁比重量级锁更慢。

#### 自旋锁

##### 自旋锁原理

前面我们讨论monitor实现锁的时候，知道monitor会阻塞和唤醒线程，线程的阻塞和唤醒需要CPU从用户态转为核心态，频繁的阻塞和唤醒对CPU来说是一件负担很重的工作，这些操作给系统的并发性能带来了很大的压力。如果物理机器有一个以上的处理器，能让两个或以上的线程同时并行执行，我们就可以让后面请求锁的那个线程“稍等一下”，但不放弃处理器的执行时间，看看持有锁的线程是否很快就会释放锁。为了让线程等待，我们只需让线程执行一个忙循环(自旋) , 这项技术就是所谓的自旋锁。

自旋等待不能代替阻塞，且先不说对处理器数量的要求，自旋等待本 身虽然避免了线程切换的开销，但它是要占用处理器时间的，因此，如果锁被占用的时间很短，自旋等待的效果就会非常好，反之，如果锁被占用的时间很长。那么自旋的线程只会白白消耗处理器资源，而不会做任何有用的工作，反而会带来性能上的浪费。因此，自旋等待的时间必须要有一定的限度，如果自旋超过了限定的次数仍然没有成功获得锁，就应当使用传统的方式去挂起线程了。自旋次数的默认值 是10次，用户可以使用参数-XX : PreBlockSpin来更改。

##### 适应性自旋锁

在JDK 6中引入了自适应的自旋锁。自适应意味着自旋的时间不再固定了，而是由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定。如果在同一个锁对象上，自旋等待刚刚成功获得过锁，并且持有锁的线程正在运行中，那么虚拟机就会认为这次自旋也很有可能再次成功，进而它将允许自旋等待持续相对更长的时间，比如100次循环。另外，如果对于某个锁，自旋很少成功获得过，那在以后要获取这个锁时将可能省略掉自旋过程，以避免浪费处理器资源。

## ReentrantLock

![ReentrantLock 加锁.jpg](https://pic.img.ski/1662618328.jpg)