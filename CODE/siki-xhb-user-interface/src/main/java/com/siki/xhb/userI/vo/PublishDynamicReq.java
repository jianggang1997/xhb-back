package com.siki.xhb.userI.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author jianggang
 * @Date 2019/5/8 18:23:52
 * @Description
 */

@ApiModel(value = "发布动态请求对象")
@Data
public class PublishDynamicReq {

    /**可见性*/
    private Integer visibility;

    /**动态内容*/
    private String content;

    /**动态中图片*/
    private List<DynamicImg> dynamicImgs;

    @Data
    public static class DynamicImg{

        /**图片文件名*/
        private String name;

        /**图片大小*/
        private String size;

        /**图片类型*/
        private String mimeType;

        /**图片地址*/
        private String url;
    }

}
