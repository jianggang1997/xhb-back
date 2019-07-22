package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/8 18:42:05
 * @Description
 */

@Data
public class UserDynamicInfo {

    private Integer id;

    private String sid;

    private String content;

    private Date pub_date;

    private String imgs;

    private Integer reg_id;

    private Date mgt_create;

    private Date mgt_modify;

}
