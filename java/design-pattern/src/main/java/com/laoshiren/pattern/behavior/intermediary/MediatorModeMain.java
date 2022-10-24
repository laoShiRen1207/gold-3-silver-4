package com.laoshiren.pattern.behavior.intermediary;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IntermediaryModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:24 2022/9/8
 */
public class MediatorModeMain {

    public static void main(String[] args) {
        //出租人
        User user0 = new User("刘女士", "10086");
        //找房人
        User user1 = new User("李先生", "10010");
        Mediator mediator = new Mediator();   //我是黑心中介

        //先把房子给中介挂上去
        mediator.register("上海静安汶水路白马程序员", user0);

        //开始找房子
        user1.find("上海静安汶水路下硅谷", mediator);
        //开始找房子
        user1.find("上海静安汶水路白马程序员", mediator);
    }

}


class Mediator {   //房产中介
    private final Map<String, User> userMap = new HashMap<>();   //在出售的房子需要存储一下

    public void register(String address, User user) {   //出售房屋的人，需要告诉中介他的房屋在哪里
        userMap.put(address, user);
    }

    public void find(String address) {   //通过此方法来看看有没有对应的房源
        User user = userMap.get(address);
        if (user == null) {
            System.out.println("没有找到对应的房源");
        } else {
            System.out.println(address+" "+user);
        }

    }
}

//用户可以是出售房屋的一方，也可以是寻找房屋的一方
class User {
    String name;
    String tel;

    public User(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    //找房子的话，需要一个中介和你具体想找的地方
    public void find(String address, Mediator mediator) {
        mediator.find(address);
    }

    @Override
    public String toString() {
        return name + " (电话：" + tel + ")";
    }
}