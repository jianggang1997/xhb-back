package com.siki.xhb.userI.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "用户登陆请求对象")
public class UserLoginReq {

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "账号密码")
    private String password;
}
