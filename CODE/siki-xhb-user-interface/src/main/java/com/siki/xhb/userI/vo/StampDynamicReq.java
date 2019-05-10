package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianggang
 * @Date 2019/5/9 12:30:03
 * @Description
 */
@ApiModel(value = "踩动态请求对象")
@Data
public class StampDynamicReq {

    @ApiModelProperty(value = "动态sid")
    private String dynamicSid;

}
