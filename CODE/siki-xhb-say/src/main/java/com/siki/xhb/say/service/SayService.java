package com.siki.xhb.say.service;

import com.siki.xhb.say.model.User;

/**
 * @author jianggang
 * @date 2019/7/22 20:35:37
 * @description
 */
public interface SayService {

    public String getUser();

    public String sayHello();

    public User getUserObject();

    public String getToken();

    public String queryUser();
}
