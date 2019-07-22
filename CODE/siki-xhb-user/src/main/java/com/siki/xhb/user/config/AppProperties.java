package com.siki.xhb.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jianggang
 * @Date 2019/5/8 19:45:12
 * @Description
 */

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String ossBaseUrl;
}
