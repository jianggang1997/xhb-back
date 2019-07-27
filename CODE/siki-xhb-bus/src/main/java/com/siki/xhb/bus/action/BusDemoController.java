package com.siki.xhb.bus.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @date 2019/7/28 01:13:47
 * @description
 */
@RestController
@RefreshScope
public class BusDemoController {

    @Value("${test.config.name}")
    String name;


    @GetMapping(value = "/test.do")
    public String test(){
        return name;
    }
}
