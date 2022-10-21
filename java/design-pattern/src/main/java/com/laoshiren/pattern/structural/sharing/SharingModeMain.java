package com.laoshiren.pattern.structural.sharing;

/**
 * @ClassName SharingModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:17 2022/9/8
 */
public class SharingModeMain {

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.service();
    }

}

class DBUtil {
    public void selectDB() {
        System.out.println("我是数据库操作...");
    }
}

class DBUtilFactory {
    //享元对象被存放在工厂中
    private static final DBUtil UTIL = new DBUtil();

    //获取享元对象
    public static DBUtil getFlyweight() {
        return UTIL;
    }
}

//用户服务
class UserService {

    public void service() {
        //通过享元工厂拿到DBUtil对象
        DBUtil util = DBUtilFactory.getFlyweight();
        //该干嘛干嘛
        util.selectDB();
    }
}