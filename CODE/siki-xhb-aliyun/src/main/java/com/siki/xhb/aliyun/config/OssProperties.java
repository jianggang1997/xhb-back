package com.siki.xhb.aliyun.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jianggang
 * @Date 2019/5/8 13:39:31
 * @Description
 */

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    private  String accessId;

    private  String accessKey;

    private  String endpoint;

    private  String bucket;

    private  String host;

    private  String callbackUrl;

    private  String dir;

}
