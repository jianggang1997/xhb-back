package com.siki.xhb.vo.constant;

public class DesKey {

    /**邮箱验证码-目标邮箱响应头*/
    public static final String VALID_CODE_RESPONSE_HEAD = "send_code";

    /**邮箱验证码-响应头验证码*/
    public static final String VALID_CODE_TO_RESPONSE_HEAD = "send_to";

    /**session 用户sid*/
    public static final String SESSION_USER_SID = "user_sid";

    /**session 用户name*/
    public static final String SESSION_USER_NAME = "user_name";

    /**请求头-用户sid*/
    public static final String REQUEST_HEAD_USER_SID = "userId";

    /**验证码验证接口请求头验证码Key*/
    public static final String VALIDATE_CODE_REQUEST_HEAD_CODE = "validateCode";

    /**验证码验证接口请求头发送目标*/
    public static final String VALIDAT_CODE_REQUEST_HEAD_TO = "sendTo";

    /**redis保存验证码记录KEY*/
    public static final String REDIS_VALIDATE_CODE_RECORD = "valid_code_record";

    /**session 保存验证码前缀*/
    public static final String SESSION_VALID_CODE_PREFICX = "valid_code";

    /**用户登录响应账号*/
    public static final String USER_LOGIN_RESPONSE_HRAD_ACCOUNT = "account";

    /**用户登录响应用户sid*/
    public static final String USER_LOGIN_RESPONSE_HEAD_SID = "sid";

    /**请求远程地址*/
    public static final String REQUEST_REMOTE_ADDR = "x_user_ip";

    /**用户登录时间前缀*/
    public static final String USER_LOGIN_TIME_PREFIX = "user_login_time_prefix";
}
