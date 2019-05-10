package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianggang
 * @Date 2019/5/9 12:40:30
 * @Description
 */

@ApiModel(value = "评论动态请求对象")
@Data
public class CommentDynamicReq {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论动态sid")
    private String dynamicSid;
}
