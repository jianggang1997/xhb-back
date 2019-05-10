package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/9 13:32:25
 * @Description
 */

@Data
public class DynamicShareInfo {

    private Integer id;

    private String sid;

    private Integer dyn_id;

    private Integer reg_id;

    private String content;

    private Date date;

    private Date mgt_create;

    private Date mgt_modify;
}
