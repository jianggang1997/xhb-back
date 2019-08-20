package com.siki.xhb.substription.sink;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author jianggang
 * @date 2019/8/5 15:22:14
 * @description
 */

@Slf4j
@EnableBinding(value = {InputInterface.class})
public class SinkReceiver {

    @StreamListener(InputInterface.input)
    public void receive(String payload){
        log.info("------------->{}",payload);
    }
}
