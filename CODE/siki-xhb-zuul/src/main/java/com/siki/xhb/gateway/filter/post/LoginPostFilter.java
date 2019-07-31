package com.siki.xhb.gateway.filter.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.siki.xhb.gateway.filter.helper.FilterHelper;
import com.siki.xhb.vo.constant.DesKey;
import com.siki.xhb.vo.constant.RsqCode;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginPostFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FilterHelper helper;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        if(!context.sendZuulResponse()){
            return false;
        }
        return helper.isLoginUri(context);
    }

    @Override
    public Object run() throws ZuulException {
        log.info("-------------->loginPostFilter");
        RequestContext context = RequestContext.getCurrentContext();
        ResObject res = new ResObject();
        ObjectMapper mapper = new ObjectMapper();
        String body = null;
        try {
            body = helper.getResponseBody(context);
            log.info("------------>{}",body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpSession session = context.getRequest().getSession();
        try {
            res = mapper.readValue(body,ResObject.class);
            /**登录成功---redis和session发用户信息*/
            if(res.getCode().equals(RsqCode.RESPONSE_SUCCESS)){
                List<Pair<String,String>> list = context.getZuulResponseHeaders();
                List<Pair<String,String>> temp = new ArrayList<>();
                String account = "";
                /**获取响应头*/
                for(Pair<String,String> pair : list){
                    if(pair.first().equals(DesKey.USER_LOGIN_RESPONSE_HRAD_ACCOUNT)){
                        session.setAttribute(DesKey.SESSION_USER_NAME, pair.second());
                        temp.add(pair);
                        account = pair.second();
                    }else if(pair.first().equals(DesKey.USER_LOGIN_RESPONSE_HEAD_SID)){
                        session.setAttribute(DesKey.SESSION_USER_SID, pair.second());
                        temp.add(pair);
                    }
                }

                /**删除响应头*/
                for(Pair<String,String> pair : temp){
                    list.remove(pair);
                }

                /**session和redis同时方登录时间-用于限制多个账号同时在线*/
                String loginTime = System.currentTimeMillis()+"";
                session.setAttribute(DesKey.USER_LOGIN_TIME_PREFIX+account,loginTime);
                stringRedisTemplate.opsForValue().set(DesKey.USER_LOGIN_TIME_PREFIX+account,loginTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
