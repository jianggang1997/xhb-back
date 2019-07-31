package com.siki.xhb.helloi.action;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jianggang
 * @date 2019/7/25 20:29:44
 * @description
 */
@FeignClient("siki-xhb-hello")
public interface HelloActionI {

    @GetMapping(value = "/getHello.do")
    String  getHello();
}
