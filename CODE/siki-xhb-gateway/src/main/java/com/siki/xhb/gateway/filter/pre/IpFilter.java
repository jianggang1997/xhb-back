package com.siki.xhb.gateway.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.siki.xhb.vo.constant.DesKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

@Slf4j
public class IpFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("------------>ipPreFile");
        RequestContext context = RequestContext.getCurrentContext();
        String remoteAddr = context.getRequest().getRemoteAddr();
        context.getZuulRequestHeaders().put(DesKey.REQUEST_REMOTE_ADDR, remoteAddr);
        return null;
    }
}
