package com.laoshiren.pattern.structural.bridging;

/**
 * @ClassName BridgingModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:15 2022/9/8
 */
public class BridgingModeMain {

    public static void main(String[] args) {
        KissTea tea = new KissTea(new Large());
        System.out.println(tea.getType());
        System.out.println(tea.getSize());
    }

}


//由具体类型的奶茶实现
interface Tea {
    //不同的奶茶返回的类型不同
    String getType();
}


//分大杯小杯中杯
interface Size {
    String getSize();
}

abstract class AbstractTea {
    //尺寸作为桥接属性存放在类中
    protected Size size;

    //在构造时需要知道尺寸属性
    protected AbstractTea(Size size) {
        this.size = size;
    }

    //具体类型依然是由子类决定
    public abstract String getType();
}


abstract class RefinedAbstractTea extends AbstractTea {
    protected RefinedAbstractTea(Size size) {
        super(size);
    }

    //添加尺寸维度获取方式
    public String getSize() {
        return size.getSize();
    }
}

class Large implements Size {

    @Override
    public String getSize() {
        return "大杯";
    }
}


//创建一个啵啵芋圆奶茶的子类
class KissTea extends RefinedAbstractTea {
    //在构造时需要指定具体的大小实现
    protected KissTea(Size size) {
        super(size);
    }

    //返回奶茶类型
    @Override
    public String getType() {
        return "啵啵芋圆奶茶";
    }
}

