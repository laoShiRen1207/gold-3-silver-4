package com.laoshiren.base.genericParadigm;

/**
 * @ClassName Main
 * @Description
 * @Author laoshiren
 * @Date 13:42 2022/9/1
 */
public class Main {

    public static void main(String[] args) {
        //直接确定Score的类型是字符串类型的成绩
        Score<String> score = new Score<String>("数据结构与算法基础", "EP074512", "优秀");
        //编译不通过，因为成员变量score类型被定为String！
        String i = score.score;
    }

}
