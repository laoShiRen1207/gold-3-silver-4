package com.laoshiren.pattern.structural.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName CglibProxyMain
 * @Description
 * @Author laoshiren
 * @Date 9:52 2022/10/21
 */
public class CglibProxyMain {

    public static void main(String[] args) {
        SubjectImpl subject = new SubjectImpl();
        //增强器，一会就需要依靠增强器来为我们生成动态代理对象
        Enhancer enhancer = new Enhancer();
        //直接选择我们需要代理的类型，直接不需要接口或是抽象类，SuperClass作为代理类的父类存在，这样我们就可以按照指定类型的方式去操作代理类了
        enhancer.setSuperclass(SubjectImpl.class);
        //设定我们刚刚编写好的代理逻辑
        enhancer.setCallback(new TestCglibProxy(subject));
        //直接创建代理类
        SubjectImpl proxy = (SubjectImpl) enhancer.create();
        //调用代理类的test方法
        proxy.test();
    }

}

//首先还是编写我们的代理逻辑
class TestCglibProxy implements MethodInterceptor {

    //这些和之前JDK动态代理写法是一样的
    private final Object target;

    public TestCglibProxy(Object target) {
        this.target = target;
    }

    //我们也是需要在这里去编写我们的代理逻辑
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("现在是由CGLib进行代理操作！" + o.getClass());
        //也是直接调用代理对象的方法即可
        return method.invoke(target, objects);
    }
}