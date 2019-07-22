package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianggang
 * @Date 2019/5/9 12:35:41
 * @Description
 */

@Data
@ApiModel(value = "分享动态请求对象")
public class ShareDynamicReq {

    @ApiModelProperty(value = "分享内容")
    private String content;

    @ApiModelProperty(value = "分享动态的sid")
    private String dynamicSid;
}
