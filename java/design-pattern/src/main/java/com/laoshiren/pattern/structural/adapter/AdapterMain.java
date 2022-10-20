package com.laoshiren.pattern.structural.adapter;

/**
 * @ClassName AdapterMain
 * @Description 适配器模式
 * @Author laoshiren
 * @Date 20:14 2022/9/8
 */
public class AdapterMain {

    public static void main(String[] args) {
        ClassAdapter adapter = new ClassAdapter();
        System.out.println("成功得到："+adapter.supply());
    }

}


class TestSupplier {

    public String doSupply(){
        return "iPhone 14 Pro Max 1TB 紫色";
    }
}

//现在的手机供应商，并不是test方法所需要的那种类型
interface Target {

    String supply();
}

/**
 * 类适配器模式
 * */
class ClassAdapter extends TestSupplier implements Target {
    //让我们的适配器继承TestSupplier并且实现Target接口

    //接着实现supply方法，直接使用TestSupplier提供的实现
    @Override
    public String supply() {
        return super.doSupply();
    }
}


/**
 * 对象适配器模式
 * 现在不再继承TestSupplier，仅实现Target
 */
class ObjectAdapter implements Target{

    TestSupplier supplier;

    public ObjectAdapter(TestSupplier supplier){
        this.supplier = supplier;
    }

    @Override
    public String supply() {
        return supplier.doSupply();
    }
}