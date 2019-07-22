package com.siki.xhb.say.action;

import com.siki.xhb.say.model.User;
import com.siki.xhb.say.service.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @date 2019/7/22 20:33:21
 * @description
 */
@RestController
public class SayController {

    @Autowired
    SayService sayService;

    @GetMapping(value = "/user.do")
    public String getUser(){
        return sayService.getUser();
    }

    @GetMapping(value = "/userObject.do")
    public String getUserObject(){
        return sayService.getUserObject().toString();
    }

    @GetMapping(value = "/say.do")
    public String say(){
        return sayService.sayHello();
    }

    @GetMapping(value = "/token.do")
    public String token(){
        return sayService.getToken();
    }

    @GetMapping(value = "/queryUser.do")
    public String query(){
        return sayService.queryUser();
    }

}
