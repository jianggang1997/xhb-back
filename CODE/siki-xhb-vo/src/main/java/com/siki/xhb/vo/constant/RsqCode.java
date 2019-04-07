package com.siki.xhb.vo.constant;

public class RsqCode {

    /**请求成功*/
    public static final Integer RESPONSE_SUCCESS = 200;

    /**请求失败*/
    public static final Integer RESPONSE_FAIL = -200;

    /**请求参数不合法*/
    public static final Integer REQUEST_PARAM_ILLEGA = -90001;

    /**用户未登录*/
    public static final Integer USER_NO_LOGIN = -90002;

    /**未发送验证码*/
    public static final Integer VALID_CODE_NO_SEND = -90003;

    /**验证码已过期*/
    public static final Integer VALID_CODE_EXPIRED = -90004;

    /**验证码错误*/
    public static final Integer VALID_CODE_FAILE = -90005;

    /**用户不存在*/
    public static final Integer USER_NOT_EXIST = -90006;

    /**用户密码错误*/
    public static final Integer USER_PASS_ERROR = -90007;

}
