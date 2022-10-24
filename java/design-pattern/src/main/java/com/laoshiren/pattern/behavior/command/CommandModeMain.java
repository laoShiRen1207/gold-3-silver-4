package com.laoshiren.pattern.behavior.command;

/**
 * @ClassName CommandModeMain
 * @Description
 * @Author laoshiren
 * @Date 20:23 2022/9/8
 */
public class CommandModeMain {

    public static void main(String[] args) {
        //先创建一个空调
        AirConditioner airConditioner = new AirConditioner();
        //直接通过遥控器来发送空调开启命令
        Controller.call(new OpenCommand(airConditioner));
    }

}

interface Receiver {
    //具体行为，这里就写一个算了
    void action();
}

//指令抽象，不同的电器有指令
abstract class Command {

    private final Receiver receiver;

    //指定此命令对应的电器（接受者）
    protected Command(Receiver receiver) {
        this.receiver = receiver;
    }

    //执行命令，实际上就是让接收者开始干活
    public void execute() {
        receiver.action();
    }
}

//遥控器只需要把我们的指令发出去就行了
class Controller {
    public static void call(Command command) {
        command.execute();
    }
}

class AirConditioner implements Receiver {
    @Override
    public void action() {
        System.out.println("空调已开启，呼呼呼");
    }
}

class OpenCommand extends Command {
    public OpenCommand(AirConditioner airConditioner) {
        super(airConditioner);
    }
}