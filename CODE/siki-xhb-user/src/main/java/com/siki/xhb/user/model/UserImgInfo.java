package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/8 18:38:27
 * @Description
 */

@Data
public class UserImgInfo {

    private Integer id;

    private String sid;

    private Integer reg_id;

    private String name;

    private Integer size;

    private String type;

    private Date add_date;

    private String url;

    private Date mgt_create;

    private Date mgt_modift;
}
