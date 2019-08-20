package com.siki.xhb.substription.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @auther jianggang
 * @date 2019/8/5 17:12:13
 * @description
 */
@Component
public interface InputInterface {

    String input = "siki-stream";

    @Input(input)
    SubscribableChannel input();
}
