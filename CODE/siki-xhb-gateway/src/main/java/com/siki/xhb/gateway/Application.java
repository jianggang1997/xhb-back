package com.siki.xhb.gateway;


import com.siki.xhb.gateway.filter.post.LoginPostFilter;
import com.siki.xhb.gateway.filter.post.ValidCodePostFilter;
import com.siki.xhb.gateway.filter.pre.IpFilter;
import com.siki.xhb.gateway.filter.pre.LoginPreFilter;
import com.siki.xhb.gateway.filter.pre.ValidCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@Slf4j
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


    @Bean
    public IpFilter ipFilter(){
        return new IpFilter();
    }


    @Bean
    public LoginPreFilter loginPreFilter(){
        return new LoginPreFilter();
    }


    @Bean
    public ValidCodeFilter validCodeFilter(){
        return new ValidCodeFilter();
    }

    @Bean
    public LoginPostFilter loginPostFilter(){
        return new LoginPostFilter();
    }

    @Bean
    public ValidCodePostFilter validCodePostFilter(){
        return new ValidCodePostFilter();
    }

}
