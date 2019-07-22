package com.siki.xhb.hello.action;

import com.netflix.client.http.HttpResponse;
import com.siki.xhb.hello.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jianggang
 * @date 2019/7/22 20:31:28
 * @description
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping(value = "/hello.do")
    public String hello(){
        log.info("------->executing");
        return "Hello Spring Cloud Ribbon!";
    }

    @GetMapping(value = "/getUser.do")
    public User getUser(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("token","21212121");
        return new User("Spring",10);
    }

    @PostMapping(value = "/queryUser.do")
    public User getUser(@RequestBody User user){
        log.info("------------>{}",user.toString());
        return user;
    }
}
