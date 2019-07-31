package com.siki.xhb.gateway.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800,redisNamespace = "xhb-session")
public class HttpSessionConfig {
}
