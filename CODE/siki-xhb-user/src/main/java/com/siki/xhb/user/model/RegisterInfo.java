package com.siki.xhb.user.model;


import lombok.Data;

import java.util.Date;

@Data
public class RegisterInfo {

    private Integer id;

    private String sid;

    private String account;

    private String pwd_md5;

    private Date reg_date;

    private String reg_ip;

    private Integer reg_type;

    private Integer info_status;

    private Date gmt_create;

    private Date gmt_modify;
}
