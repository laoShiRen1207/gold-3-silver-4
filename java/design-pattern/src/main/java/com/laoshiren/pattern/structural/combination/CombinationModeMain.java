package com.laoshiren.pattern.structural.combination;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CombinationModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:15 2022/9/8
 */
public class CombinationModeMain {

    public static void main(String[] args) {
        Directory outer = new Directory("空文件夹");   //新建一个外层目录
        Directory inner = new Directory("1-3");   //新建一个内层目录
        outer.addComponent(inner);
        outer.addComponent(new File("1"));   //在内层目录和外层目录都添加点文件，注意别导错包了
        inner.addComponent(new File("2"));
        inner.addComponent(new File("3"));
        Directory inner2 = new Directory("4");
        inner2.addComponent(new File("4"));
        outer.addComponent(inner2);
        outer.test();    //开始执行文件名称修改操作
    }

}


abstract class Component {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //添加子组件
    public abstract void addComponent(Component component);

    //删除子组件
    public abstract void removeComponent(Component component);

    //获取子组件
    public abstract Component getChild(int index);

    //执行对应的业务方法，比如修改文件名称
    public abstract void test();
}


//目录可以包含多个文件或目录
class Directory extends Component {

    public Directory(String name){
        this.setName(name);
    }

    //这里我们使用List来存放目录中的子组件
    List<Component> child = new ArrayList<>();

    @Override
    public void addComponent(Component component) {
        child.add(component);
    }

    @Override
    public void removeComponent(Component component) {
        child.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return child.get(index);
    }

    //将继续调用所有子组件的test方法执行业务
    @Override
    public void test() {
        System.out.println(getName());
        child.forEach(Component::test);
    }
}


//文件就相当于是树叶，无法再继续添加子组件了
class File extends Component {

    public File(String name ){
        this.setName(name);
    }

    //不支持这些操作了
    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    //不支持这些操作了
    @Override
    public void removeComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    //不支持这些操作了
    @Override
    public Component getChild(int index) {
        throw new UnsupportedOperationException();
    }

    //具体的名称修改操作
    @Override
    public void test() {
        System.out.println( "\t 文件名称修改成功！" + getName());
    }
}

