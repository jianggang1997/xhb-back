package com.siki.xhb.hello.action;

import com.siki.xhb.hello.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author jianggang
 * @date 2019/7/22 20:31:28
 * @description
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping(value = "/hello.do")
    public String hello() throws Exception {
        log.info("------->executing");
        double round = Math.random()*10;
        log.info("{}",round);
        if(round>5){
            throw new Exception();
        }
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getResponse();
        Enumeration<String> headers = request.getHeaderNames();
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String,String[]> entry : parameterMap.entrySet()){
            log.info("key:{}----value:{}",entry.getKey(),entry.getValue());
        }
        while (headers.hasMoreElements()){
            log.info("------->:{}",request.getHeader(headers.nextElement()));
        }
        for(String str : headerNames){
            log.info("------->:{}",response.getHeader(str));
        }
        return "Hello Spring Cloud Ribbon!";
    }

    @GetMapping(value = "/getUser.do")
    public User getUser(){
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getResponse();
        Enumeration<String> headers = request.getHeaderNames();
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String,String[]> entry : parameterMap.entrySet()){
            log.info("key:{}----value:{}",entry.getKey(),entry.getValue());
        }
        while (headers.hasMoreElements()){
            log.info("------->:{}",request.getHeader(headers.nextElement()));
        }
        for(String str : headerNames){
            log.info("------->:{}",response.getHeader(str));
        }
        response.setHeader("token","21212121");
        return new User("Spring",10);
    }

    @PostMapping(value = "/queryUser.do")
    public User getUser(@RequestBody User user){
        log.info("------------>{}",user.toString());
        return user;
    }
}
