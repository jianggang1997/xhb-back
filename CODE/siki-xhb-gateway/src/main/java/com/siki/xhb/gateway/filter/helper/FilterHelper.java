package com.siki.xhb.gateway.filter.helper;


import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class FilterHelper {

    @Autowired
    private XhbGatewayProperties xhbGatewayProperties;

    /**
     * 判断接口是否需要登录验证
     * @return
     */
    public boolean isValidateLogin(RequestContext requestContext){
        String uri = formatUri(requestContext.getRequest().getRequestURI());
        String[] uris = xhbGatewayProperties.getNoValidateLogin().split(";");
        return !isExist(uris,uri);
    }

    /***
     * 判断是否为登录接口
     * @param requestContext
     * @return
     */
    public boolean isLoginUri(RequestContext requestContext){
        String uri = formatUri(requestContext.getRequest().getRequestURI());
        String[] uris = xhbGatewayProperties.getLoginUri().split(";");
        return isExist(uris,uri);
    }


    /**
     * 判断接口是否需要验证验证码
     * @param requestContext
     * @return
     */
    public boolean isValidateCode(RequestContext requestContext){
        String uri = formatUri(requestContext.getRequest().getRequestURI());
        String[] uris = xhbGatewayProperties.getValidateCode().split(";");
        return isExist(uris,uri);
    }

    /**
     * 判断是否为登出接口
     * @return
     */
    public boolean isLogout(RequestContext requestContext){
        String uri = formatUri(requestContext.getRequest().getRequestURI());
        String[] uris = xhbGatewayProperties.getLogout().split(";");
        return isExist(uris,uri);
    }


    /**
     * 判断是否为发送验证码接口
     * @param requestContext
     * @return
     */
    public boolean isSendValidCode(RequestContext requestContext){
        String uri = formatUri(requestContext.getRequest().getRequestURI());
        String[] uris = xhbGatewayProperties.getSendValidCodeUri().split(";");
        return isExist(uris,uri);
    }

    /**
     * 获取今日剩余秒数
     * @return
     */
    public long getRemSecond(){
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        long zero = calendar.getTime().getTime();
        return (zero-now)/1000;
    }

    /**
     * 获取响应体
     * @param context
     * @return
     */
    public String getResponseBody(RequestContext context) throws IOException {
        InputStream is = context.getResponseDataStream();
        String body = "";
        try {
            body = StreamUtils.copyToString(is, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
           is.close();
        }
        context.setResponseBody(body);
        return body;
    }

    /**
     * 判断目标uri是否存在
     * @param strs 配置的uri集合
     * @param target 目标uri
     * @return
     */
    private boolean isExist(String[] strs,String target){
        for(String str : strs){
            if(target.equals(str)){
                return true;
            }
        }
        return false;
    }

    /***
     * 得到具体的请求uri
     * @return
     */
    private String formatUri(String uri){
        int index = uri.lastIndexOf('/');
        return uri.substring(index+1);
    }
}
