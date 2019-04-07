package com.siki.xhb.gateway.filter.pre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.siki.xhb.gateway.filter.helper.FilterHelper;
import com.siki.xhb.gateway.filter.helper.XhbGatewayProperties;
import com.siki.xhb.gateway.model.ValidCodeRecord;
import com.siki.xhb.vo.constant.DesKey;
import com.siki.xhb.vo.constant.RsqCode;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class ValidCodeFilter extends ZuulFilter {

    @Autowired
    private FilterHelper helper;

    @Autowired
    private XhbGatewayProperties properties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        return helper.isValidateCode(context);
    }

    @Override
    public Object run() throws ZuulException {
        log.info("------------>验证码验证过滤器-pre");
        RequestContext context = RequestContext.getCurrentContext();
        HttpSession session = context.getRequest().getSession();
        ResObject res = new ResObject();
        ObjectMapper mapper = new ObjectMapper();
        String validateCode = context.getRequest().getHeader(DesKey.VALIDATE_CODE_REQUEST_HEAD_CODE);
        String sendTo = context.getRequest().getHeader(DesKey.VALIDAT_CODE_REQUEST_HEAD_TO);
        log.info("------------------test{}",DesKey.SESSION_VALID_CODE_PREFICX+sendTo);
        log.info("------------------code{}",session.getAttribute(DesKey.SESSION_VALID_CODE_PREFICX+sendTo));
        String CodeRecordJosn = (String)session.getAttribute(DesKey.SESSION_VALID_CODE_PREFICX+sendTo);
        try {
            /**验证码未发送*/
            if(CodeRecordJosn == null){
                res.setCode(RsqCode.VALID_CODE_NO_SEND);
                res.setMessage("未发送验证码！");
                context.setResponseBody(mapper.writeValueAsString(res));
                context.addZuulResponseHeader("Content-Type","application/json;charset=UTF-8");
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpServletResponse.SC_OK);
                return null;
            }else {
                ValidCodeRecord record = mapper.readValue(CodeRecordJosn,ValidCodeRecord.class);
                /**验证码已过期*/
                if((System.currentTimeMillis()/1000-record.getTimestamp()/1000)>properties.getValidCodeLiveTime()){
                    res.setCode(RsqCode.VALID_CODE_NO_SEND);
                    res.setMessage("验证码已过期！");
                    context.setResponseBody(mapper.writeValueAsString(res));
                    context.addZuulResponseHeader("Content-Type","application/json;charset=UTF-8");
                    context.setSendZuulResponse(false);
                    context.setResponseStatusCode(HttpServletResponse.SC_OK);
                }
                /**验证码不匹配*/
                else if(!(sendTo.equals(record.getTo())&&validateCode.equals(record.getCode()))){
                    res.setCode(RsqCode.VALID_CODE_NO_SEND);
                    res.setMessage("验证码错误！");
                    context.setResponseBody(mapper.writeValueAsString(res));
                    context.addZuulResponseHeader("Content-Type","application/json;charset=UTF-8");
                    context.setSendZuulResponse(false);
                    context.setResponseStatusCode(HttpServletResponse.SC_OK);
                /**验证通过--删除，一次有效*/
                }else {
                    session.removeAttribute(DesKey.SESSION_VALID_CODE_PREFICX +sendTo);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
