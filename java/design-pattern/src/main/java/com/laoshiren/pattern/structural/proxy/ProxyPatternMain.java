package com.laoshiren.pattern.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName ProxyPatternMain
 * @Description
 * @Author laoshiren
 * @Date 20:16 2022/9/8
 */
public class ProxyPatternMain {

    public static void main(String[] args) {
        SubjectImpl subject = new SubjectImpl();
        InvocationHandler handler = new TestProxy(subject);
        ISubject proxy = (ISubject) Proxy.newProxyInstance(
                //需要传入被代理的类的类加载器
                subject.getClass().getClassLoader(),
                //需要传入被代理的类的接口列表
                subject.getClass().getInterfaces(),
                //最后传入我们实现的代理处理逻辑实现类
                handler);
        //比如现在我们调用代理类的test方法，那么就会进入到我们上面TestProxy中invoke方法，走我们的代理逻辑
        proxy.test();
    }

}

abstract class Subject implements ISubject {
}

//此类无法直接使用，需要我们进行代理
class SubjectImpl implements ISubject {

    @Override
    public void test() {
        System.out.println("我是测试方法！");
    }
}

//为了保证和Subject操作方式一样，保证透明性，也得继承
class MyProxy extends Subject {
    //被代理的对象（甚至可以多重代理）
    Subject target;

    public MyProxy(Subject subject) {
        this.target = subject;
    }

    //由代理去执行被代理对象的方法，并且我们还可以在前后添油加醋
    @Override
    public void test() {
        System.out.println("代理前绕方法");
        target.test();
        System.out.println("代理后绕方法");
    }
}

interface ISubject {
    void test();
}

class TestProxy implements InvocationHandler {    //代理类，需要实现InvocationHandler接口

    private final Object object;   //这里需要保存一下被代理的对象，下面需要用到

    public TestProxy(Object object) {
        this.object = object;
    }

    //此方法就是调用代理对象的对应方法时会进入，这里我们就需要编写如何进行代理了
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method就是调用的代理对象的哪一个方法，args是实参数组
        //proxy就是生成的代理对象了，我们看看是什么类型的
        System.out.println("代理的对象：" + proxy.getClass());
        //在代理中调用被代理对象原本的方法，因为你是代理，还是得执行一下别人的业务，当然也可以不执行，但是这样就失去代理的意义了，注意要用上面的object
        Object res = method.invoke(object, args);
        //看看返回值是什么
        System.out.println("方法调用完成，返回值为：" + res);
        //返回返回值
        return res;
    }
}



