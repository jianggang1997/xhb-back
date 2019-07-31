package com.siki.xhb.gateway.model;


import lombok.Data;

@Data
public class ValidCodeRecord {

    private String code;

    private String to;

    private long timestamp;
}
