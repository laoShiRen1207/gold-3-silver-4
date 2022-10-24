package com.laoshiren.pattern.behavior.chain;

import java.util.Optional;

/**
 * @ClassName ChainMode
 * @Description
 * @Author laoshiren
 * @Date 20:23 2022/9/8
 */
public class ChainMode {

    public static void main(String[] args) {
        Handler handler = new FirstHandler();  //一面首当其冲
        handler
                .connect(new SecondHandler())   //继续连接二面和三面
                .connect(new ThirdHandler());
        handler.handle();   //开始面试
    }


}


abstract class Handler {

    //这里我们就设计责任链以单链表形式存在，这里存放后继节点
    protected Handler successor;

    //拼接后续节点
    public Handler connect(Handler successor) {
        this.successor = successor;
        //这里返回后继节点，方便我们一会链式调用
        return successor;
    }

    public void handle() {
        //由不同的子类实现具体处理过程
        this.doHandle();
        Optional
                .ofNullable(successor)
                //责任链上如果还有后继节点，就继续向下传递
                .ifPresent(Handler::handle);
    }

    //结合上节课学习的模板方法，交给子类实现
    public abstract void doHandle();
}


class FirstHandler extends Handler {
    //用于一面的处理器
    @Override
    public void doHandle() {
        System.out.println("============= 白马程序员一面 ==========");
    }
}

class SecondHandler extends Handler {
    @Override
    public void doHandle() {
        System.out.println("============= 白马程序员二面 ==========");
    }
}

class ThirdHandler extends Handler{
    @Override
    public void doHandle() {
        System.out.println("============= 白马程序员三面 ==========");
    }
}

