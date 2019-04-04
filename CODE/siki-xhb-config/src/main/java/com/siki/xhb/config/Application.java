package com.siki.xhb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

/**
 * @author jianggang
 * @Date 2019/3/29 16:42:40
 * @Description
 */

@Slf4j
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        ApplicationContext context = SpringApplication.run(Application.class,args);
        Environment environment = context.getBean(Environment.class);
        String applicationName = environment.getProperty("spring.application.name");
        watch.stop();
        log.info("{} 启动完毕，times={}s", applicationName, watch.getTotalTimeSeconds());
    }
}
