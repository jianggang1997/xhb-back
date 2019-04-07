package com.siki.xhb.userI.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户修改密码求情对象")
public class ModifyPassReq {

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "新密码")
    private String newPass;
}
