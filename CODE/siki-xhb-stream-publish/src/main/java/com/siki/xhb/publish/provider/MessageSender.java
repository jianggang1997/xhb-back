package com.siki.xhb.publish.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author jianggang
 * @date 2019/8/5 16:46:03
 * @description
 */
@Slf4j
@EnableBinding(value = {OutPutInterface.class})
@EnableScheduling
public class MessageSender {

    @Autowired
    OutPutInterface outPutInterface;

    @Scheduled(initialDelay = 2000 , fixedRate = 4000)
    public void sendMessage1(){
        Message message = MessageBuilder.withPayload(new Person("测试消息",20))
                .setHeader("partitionKey",1)
                .build();
        outPutInterface.output().send(message);
        log.info("------->{}","发送消息成功！");
    }
}
