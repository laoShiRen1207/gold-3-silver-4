package com.laoshiren.pattern.creative.prototype;

/**
 * @ClassName PrototypeMain
 * @Description
 * @Author laoshiren
 * @Date 20:14 2022/9/8
 */
public class PrototypeMain {

    public static void main(String[] args) throws CloneNotSupportedException {
        Student student = new Student();
        student.setName("laoshiren");

        Object clone = student.clone();

        System.out.println(student.equals(clone));
    }

}


class Student implements Cloneable  {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.name = new String(name);
        return student;   //成员拷贝完成后，再返回
    }

}
