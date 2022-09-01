package com.laoshiren.base.genericParadigm;

/**
 * @ClassName Score
 * @Description
 * @Author laoshiren
 * @Date 13:42 2022/9/1
 */
//将Score转变为泛型类<T>
public class Score<T> {
    String name;
    String id;
    //T为泛型，根据用户提供的类型自动变成对应类型
    T score;

    public Score(String name, String id, T score) {   //提供的score类型即为T代表的类型
        this.name = name;
        this.id = id;
        this.score = score;
    }
}
