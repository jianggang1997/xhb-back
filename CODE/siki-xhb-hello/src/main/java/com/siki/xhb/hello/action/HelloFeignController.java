package com.siki.xhb.hello.action;

import com.siki.xhb.helloi.action.HelloActionI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @date 2019/7/25 20:31:37
 * @description
 */

@Slf4j
@RestController
public class HelloFeignController implements HelloActionI {

    @Override
    public String getHello() {
        log.info("---------->{}","executing");
        return "Hello Spring Cloud Feign";
    }
}
