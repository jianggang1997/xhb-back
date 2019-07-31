package com.siki.xhb.say.action;

import com.siki.xhb.helloi.action.HelloActionI;
import com.siki.xhb.say.service.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @date 2019/7/25 20:33:40
 * @description
 */
@RestController
public class SayFeignController {

    @Autowired
    HelloActionI helloActionI;

    @Autowired
    SayService sayService;

    @GetMapping(value = "/getSayHello.do")
    public String getSayHello(){
        return helloActionI.getHello();
    }


}
