package com.siki.xhb.gateway.filter.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.siki.xhb.gateway.filter.helper.FilterHelper;
import com.siki.xhb.gateway.model.RedisValidCodeRecord;
import com.siki.xhb.gateway.model.ValidCodeRecord;
import com.siki.xhb.vo.constant.DesKey;
import com.siki.xhb.vo.constant.RsqCode;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ValidCodePostFilter extends ZuulFilter {

    @Autowired
    private FilterHelper helper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        if(!context.sendZuulResponse()){
            return false;
        }
        return helper.isSendValidCode(context);
    }

    @Override
    public Object run() throws ZuulException {
        log.info("------------------>validCodePostFilter");
        RequestContext context = RequestContext.getCurrentContext();
        HttpSession session = context.getRequest().getSession();
        ResObject res = new ResObject();
        ObjectMapper mapper = new ObjectMapper();
        String body = null;
        try {
            body = helper.getResponseBody(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ResObject resObject = mapper.readValue(body,ResObject.class);
            if(resObject.getCode().equals(RsqCode.RESPONSE_SUCCESS)){
                List<Pair<String,String>> list = context.getZuulResponseHeaders();
                List<Pair<String,String>> temp = new ArrayList<>();
                String to = "";
                String validCode = "";
                for(Pair<String,String> pair : list){
                    if(pair.first().equals(DesKey.VALID_CODE_RESPONSE_HEAD)){
                        validCode = pair.second();
                        temp.add(pair);
                    }else if(pair.first().equals(DesKey.VALID_CODE_TO_RESPONSE_HEAD)){
                        to = pair.second();
                        temp.add(pair);
                    }
                }
                /**删除响应头*/
                for(Pair<String,String> pair : temp){
                    list.remove(pair);
                }

                String redisRecordJosn = stringRedisTemplate.opsForValue().get(DesKey.REDIS_VALIDATE_CODE_RECORD +to);
                if(redisRecordJosn != null){
                    RedisValidCodeRecord redisValidCodeRecord = mapper.readValue(redisRecordJosn, RedisValidCodeRecord.class);
                    redisValidCodeRecord.setNum(redisValidCodeRecord.getNum()+1);
                    redisValidCodeRecord.setNextSendTime(System.currentTimeMillis());
                    String value = mapper.writeValueAsString(redisValidCodeRecord);
                    stringRedisTemplate.opsForValue().set(DesKey.REDIS_VALIDATE_CODE_RECORD +to,value);
                    stringRedisTemplate.expire(DesKey.REDIS_VALIDATE_CODE_RECORD +to,helper.getRemSecond(), TimeUnit.SECONDS);
                }else{
                    RedisValidCodeRecord redisValidCodeRecord = new RedisValidCodeRecord();
                    redisValidCodeRecord.setNum(1);
                    redisValidCodeRecord.setNextSendTime(System.currentTimeMillis());
                    String value = mapper.writeValueAsString(redisValidCodeRecord);
                    stringRedisTemplate.opsForValue().set(DesKey.REDIS_VALIDATE_CODE_RECORD +to,value);
                    stringRedisTemplate.expire(DesKey.REDIS_VALIDATE_CODE_RECORD +to,helper.getRemSecond(), TimeUnit.SECONDS);
                }

                ValidCodeRecord validCodeRecord = new ValidCodeRecord();
                validCodeRecord.setCode(validCode);
                validCodeRecord.setTo(to);
                validCodeRecord.setTimestamp(System.currentTimeMillis());
                String validCodeRecordJosn = mapper.writeValueAsString(validCodeRecord);
                session.setAttribute(DesKey.SESSION_VALID_CODE_PREFICX+to,validCodeRecordJosn);
                log.info("------------------test{}",DesKey.SESSION_VALID_CODE_PREFICX+to);
                log.info("------------------test{}",session.getAttribute(DesKey.SESSION_VALID_CODE_PREFICX+to));
                session.setAttribute("a","a");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
