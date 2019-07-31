package com.siki.xhb.hello.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jianggang
 * @date 2019/7/22 21:06:39
 * @description
 */
@Data
@AllArgsConstructor
public class User {
    private  String name;

    private Integer age;
}
