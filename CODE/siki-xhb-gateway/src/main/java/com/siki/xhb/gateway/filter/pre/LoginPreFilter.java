package com.siki.xhb.gateway.filter.pre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginPreFilter extends ZuulFilter {


    @Autowired
    private FilterHelper helper;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        return helper.isValidateLogin(context);
    }

    @Override
    public Object run() throws ZuulException {
        log.info("------------>登录验证过滤器-pre");
        ResObject res = new ResObject();
        RequestContext context = RequestContext.getCurrentContext();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = context.getRequest().getSession();
        try {
            if(helper.isLogout(context)){
                session.invalidate();
                res.setCode(RsqCode.RESPONSE_SUCCESS);
                res.setMessage("登出成功！");

                context.setResponseBody(mapper.writeValueAsString(res));
                context.addZuulResponseHeader("Content-Type","application/json;charset=UTF-8");
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpServletResponse.SC_OK);
                return null;

            }

            String usersid = (String) session.getAttribute(DesKey.SESSION_USER_SID);
            if(usersid == null){
                res.setCode(RsqCode.USER_NO_LOGIN);
                res.setMessage("用户未登录，请先登录！");
                context.setResponseBody(mapper.writeValueAsString(res));
                context.addZuulResponseHeader("Content-Type","application/json;charset=UTF-8");
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpServletResponse.SC_OK);
                return null;
            }else {
                context.addZuulRequestHeader(DesKey.REQUEST_HEAD_USER_SID,usersid);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
