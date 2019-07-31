package com.siki.xhb.gateway.filter.helper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gateway.config")
public class XhbGatewayProperties {

    /**需要验证验证码的接口*/
    private String validateCode;

    /**不需要验证登录的接口*/
    private String noValidateLogin;

    /**登出接口*/
    private String logout;

    /**验证码有效时间*/
    private int validCodeLiveTime;

    /**短信下发最短间隔*/
    private int validMinInterval;

    /**登录接口地址*/
    private String loginUri;

    /**发验证码接口地址*/
    private String sendValidCodeUri;
}
