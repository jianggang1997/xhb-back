package com.siki.xhb.bus.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @date 2019/7/28 01:13:47
 * @description
 */
@RestController
public class BusDemoController {

    @Autowired
    BusProperties busProperties;

    @GetMapping(value = "/test.do")
    public String test(){
        return busProperties.getName();
    }
}
