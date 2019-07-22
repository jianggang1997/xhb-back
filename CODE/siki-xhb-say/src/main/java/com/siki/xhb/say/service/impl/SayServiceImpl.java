package com.siki.xhb.say.service.impl;

import com.siki.xhb.say.model.User;
import com.siki.xhb.say.service.SayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author jianggang
 * @date 2019/7/22 20:35:55
 * @description
 */
@Slf4j
@Service
public class SayServiceImpl implements SayService {

    @Autowired
    RestTemplate restTemplate;


    @Override
    public String getUser() {
        ResponseEntity<String> response  =  restTemplate.getForEntity("http://siki-xhb-hello/getUser.do",String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return response.getBody();
        }
        return null;
    }

    @Override
    public String sayHello() {
        return restTemplate.getForObject("http://siki-xhb-hello/hello.do",String.class);
    }

    @Override
    public User getUserObject() {
        return restTemplate.getForObject("http://siki-xhb-hello/getUser.do", User.class);
    }

    @Override
    public String getToken() {
        HttpHeaders httpHeaders = restTemplate.headForHeaders("http://siki-xhb-hello/getUser.do");
        return httpHeaders.get("token").get(0);
    }

    @Override
    public String queryUser() {
        User user = new User();
        user.setAge(11);
        user.setName("Spring Cloud");
        HttpEntity<User> request = new HttpEntity<>(user);
        User user1 = restTemplate.postForObject("http://siki-xhb-hello/queryUser.do", request, User.class);
        return user1.toString();
    }


}
