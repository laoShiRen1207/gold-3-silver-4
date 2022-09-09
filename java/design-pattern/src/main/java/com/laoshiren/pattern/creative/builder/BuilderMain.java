package com.laoshiren.pattern.creative.builder;

import lombok.Data;

/**
 * @ClassName BuilderMain
 * @Description
 * @Author laoshiren
 * @Date 20:09 2022/9/8
 */
public class BuilderMain {




    public static void main(String[] args) {
        Student student = Student.builder()
                .id(1)
                .age(2)
                .name("laoshiren")
                .build();
        System.out.println(student);
    }


}

@Data
class Student {
    private int id;
    private int age;
    private String name;

    //通过builder方法直接获取建造者
    public static StudentBuilder builder(){
        return new StudentBuilder();
    }

}


class StudentBuilder{
    private int id;
    private int age;
    private String name;

    public StudentBuilder id(int id) {
        this.id = id;
        return this;
    }

    public StudentBuilder age(int age) {
        this.age = age;
        return this;
    }

    public StudentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Student build(){
        Student student = new Student();
        student.setId(this.id);
        student.setAge(this.age);
        student.setName(this.name);
        return student;
    }

}
