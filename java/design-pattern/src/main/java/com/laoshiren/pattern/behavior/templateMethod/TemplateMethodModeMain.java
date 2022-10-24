package com.laoshiren.pattern.behavior.templateMethod;

/**
 * @ClassName TemplateMethodModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:22 2022/9/8
 */
public class TemplateMethodModeMain {

    public static void main(String[] args) {
        AbstractDiagnosis diagnosis = new ColdDiagnosis();
        diagnosis.test();
    }

}

abstract class AbstractDiagnosis {

    public abstract void fallIll();

    public void test() {
        this.fallIll();
        System.out.println("1 >> 先挂号");
        System.out.println("2 >> 等待叫号");
        //由于现在不知道该开什么处方，所以只能先定义一下行为，然后具体由子类实现
        //大致的流程先定义好就行
        this.prescribe();
        //开药同理
        this.medicine();
    }

    //开处方操作根据具体病症决定了
    public abstract void prescribe();

    //拿药也是根据具体的处方去拿
    public abstract void medicine();
}

class ColdDiagnosis extends AbstractDiagnosis {

    @Override
    public void fallIll() {
        System.out.println("0 >> 今天头好晕 感冒了 请假去医院 ");
    }

    @Override
    public void prescribe() {
        System.out.println("3 >> 一眼丁真，鉴定为假，这不是感冒，纯粹是想摆烂");
    }

    @Override
    public void medicine() {
        System.out.println("4 >> 开点头孢回去吃吧");
    }
}