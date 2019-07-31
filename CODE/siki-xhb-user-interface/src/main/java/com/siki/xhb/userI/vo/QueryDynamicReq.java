package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianggang
 * @Date 2019/5/9 12:07:04
 * @Description
 */

@ApiModel(value = "查询校园动态")
@Data
public class QueryDynamicReq {

    @ApiModelProperty(value = "查询类型")
    private String type;

    @ApiModelProperty(value = "当前页")
    private Integer currPage;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;
}
