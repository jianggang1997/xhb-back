package com.siki.xhb.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author jianggang
 * @date 2019/7/22 20:17:29
 * @description
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.siki.xhb"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
