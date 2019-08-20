package com.siki.xhb.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author jianggang
 * @date 2019/8/19 11:38:02
 * @description
 */

@Slf4j
public class FilterConfig {


    @Bean
    @Order(-1)
    public GlobalFilter loginFilter(){
        return (exchange,chain) -> {
                log.info("------>order  is -1 pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("------>order is -1 post filter");
            }));
        };
    }
}
