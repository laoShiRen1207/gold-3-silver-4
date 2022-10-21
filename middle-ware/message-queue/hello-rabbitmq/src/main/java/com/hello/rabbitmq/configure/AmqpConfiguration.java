package com.hello.rabbitmq.configure;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AmqpConfiguration
 * @Description
 * @Author laoshiren
 * @Date 16:34 2022/10/21
 */
@Configuration
public class AmqpConfiguration {


    @Bean("headerExchange")  //注意这里返回的是HeadersExchange
    public HeadersExchange exchange(){
        return ExchangeBuilder
                .headersExchange("amq.headers")  //RabbitMQ为我们预置了两个，这里用第一个就行
                .build();
    }

    @Bean("yydsQueue")
    public Queue queue(){
        return QueueBuilder.durable("yyds").build();
    }

    @Bean("binding")
    public Binding binding2(@Qualifier("headerExchange") HeadersExchange exchange,  //这里和上面一样的类型
                            @Qualifier("yydsQueue") Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(exchange)   //使用HeadersExchange的to方法，可以进行进一步配置
                //.whereAny("a", "b").exist();   这个是只要存在任意一个指定的头部Key就行
                //.whereAll("a", "b").exist();   这个是必须存在所有指定的的头部Key
                .where("test").matches("hello");   //比如我们现在需要消息的头部信息中包含test，并且值为hello才能转发给我们的消息队列
        //.whereAny(Collections.singletonMap("test", "hello")).match();  传入Map也行，批量指定键值对
    }

}
