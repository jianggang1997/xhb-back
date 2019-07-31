package com.siki.xhb.bus.action;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author jianggang
 * @date 2019/7/28 01:12:15
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "test.config")
public class BusProperties {

    private String name;
}
