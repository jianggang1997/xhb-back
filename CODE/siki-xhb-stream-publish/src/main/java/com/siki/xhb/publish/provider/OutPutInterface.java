package com.siki.xhb.publish.provider;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author jianggang
 * @date 2019/8/5 16:47:33
 * @description
 */

@Component
public interface OutPutInterface {

    String output = "siki-stream";

    @Output(output)
    MessageChannel output();

}
