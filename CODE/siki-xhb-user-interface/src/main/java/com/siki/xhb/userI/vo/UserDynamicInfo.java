package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author jianggang
 * @Date 2019/5/9 12:13:33
 * @Description
 */
@ApiModel(value = "动态信息")
@Data
public class UserDynamicInfo {

    private String userSid;

    private String userName;

    private String school;

    private long pubDate;

    private String content;

    private List<String> imgs;

    private Integer shareNum;

    private Integer commentNum;

    private Integer stampNum;
}
