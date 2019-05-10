package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mockito.stubbing.ValidableAnswer;

/**
 * @author jianggang
 * @Date 2019/5/9 12:44:58
 * @Description
 */

@ApiModel(value = "回复评论请求对象")
@Data
public class ReplyCommentReq {

    @ApiModelProperty(value = "回复类型")
    private Integer commentType;

    @ApiModelProperty(value = "回复内容")
    private String content;

    @ApiModelProperty(value = "根评论sid")
    private String rootCommentSid;

    @ApiModelProperty(value = "父评论sid")
    private String parentCommentSid;

    @ApiModelProperty(value = "回复目标用户sid")
    private String targetUserSid;
}
