package com.siki.xhb.user.model;

import lombok.Data;

import java.util.Date;

/**
 * @author jianggang
 * @Date 2019/5/9 13:00:45
 * @Description
 */

@Data
public class DynamicStampInfo {

    /***id*/
    private Integer id;

    private String sid;

    private Integer dyn_id;

    private Integer reg_id;

    private Date date;

    private Date mgt_create;

    private Date mgt_modify;
}
