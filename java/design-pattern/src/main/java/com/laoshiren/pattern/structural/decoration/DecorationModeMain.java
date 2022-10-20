package com.laoshiren.pattern.structural.decoration;

/**
 * @ClassName DecorationModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:16 2022/9/8
 */
public class DecorationModeMain {

    public static void main(String[] args) {
        Base base = new BaseImpl();
        //将Base实现装饰一下
        Decorator decorator = new DecoratorImpl(base);
        //装饰者还可以嵌套
        Decorator outer = new DecoratorImpl(decorator);

        decorator.test();

        outer.test();
    }

}

//顶层抽象类，定义了一个test方法执行业务
abstract class Base {
    public abstract void test();
}

//具体的业务方法
class BaseImpl extends Base {
    @Override
    public void test() {
        System.out.println("我是业务方法");
    }
}
//装饰者需要将装饰目标组合到类中
class Decorator extends Base {

    protected Base base;

    public Decorator(Base base) {
        this.base = base;
    }

    //这里暂时还是使用目标的原本方法实现
    @Override
    public void test() {
        base.test();
    }
}
//装饰实现
class DecoratorImpl extends Decorator {

    public DecoratorImpl(Base base) {
        super(base);
    }

    @Override
    public void test() {    //对原本的方法进行装饰，我们可以在前后都去添加额外操作
        System.out.println("装饰方法：我是操作前逻辑");
        super.test();
        System.out.println("装饰方法：我是操作后逻辑");
    }
}