package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/9 13:36:05
 * @Description
 */
@Data
public class CommentReplyInfo {

    private Integer id;

    private String sid;

    private Integer reply_type;

    private String content;

    private Date date;

    private Integer reply_id;

    private Integer comm_id;

    private Integer from_id;

    private Integer to_id;

    private Date mgt_create;

    private Date mgt_modify;
}
