package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/9 13:34:22
 * @Description
 */

@Data
public class DynamicCommentInfo {

    private Integer id;

    private String sid;

    private String content;

    private Date date;

    private Integer reg_id;

    private Integer dyn_id;

    private Date mgt_create;

    private Date mgt_modify;
}
