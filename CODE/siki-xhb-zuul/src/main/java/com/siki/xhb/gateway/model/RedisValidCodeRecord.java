package com.siki.xhb.gateway.model;

import lombok.Data;

@Data
public class RedisValidCodeRecord {

    private Integer num;

    private long nextSendTime;
}
