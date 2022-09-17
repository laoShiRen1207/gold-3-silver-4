package com.hello.springboot.rocket.controller;

import com.hello.springboot.rocket.producer.SimpleProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ProducerController
 * @Description
 * @Author laoshiren
 * @Date 17:02 2022/9/17
 */
@RestController
public class ProducerController {

    @Resource
    private SimpleProducer simpleProducer;

    @GetMapping("sync/send")
    public ResponseEntity<SendResult> syncSend(){
        SendResult send = simpleProducer.syncSend();
        return new ResponseEntity<>(send,HttpStatus.OK);
    }

    @GetMapping("async/send")
    public ResponseEntity<Map<String,String>> asyncSend(){
        simpleProducer.asyncSend();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("send","ok");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("transaction/send")
    public ResponseEntity<SendResult> transactionSend(){
        SendResult send = simpleProducer.transactionSend();
        return new ResponseEntity<>(send,HttpStatus.OK);
    }

}
