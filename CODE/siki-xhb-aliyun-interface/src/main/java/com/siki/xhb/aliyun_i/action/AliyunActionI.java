package com.siki.xhb.aliyun_i.action;

import com.siki.xhb.vo.model.ResObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Auther jianggang
 * @Date 2019/5/8 13:05:57
 * @Description
 */

@Api(value = "接入阿里云产品接口")
@FeignClient(value = "siki-xhb-aliyun")
public interface AliyunActionI {

    @ApiOperation(value = "获取OSS上传信息签名")
    @GetMapping(value = "/getOssSignature.do")
    ResObject getOssSignature(@RequestParam(value = "filePrefix") String filePrefix);

    @ApiOperation(value = "OSS服务上传服务回调")
    @PostMapping(value = "/ossCallback.do")
    ResObject ossServiceCallback(@RequestParam(value = "filename")String filename,
                                 @RequestParam(value = "size")String size,
                                 @RequestParam(value = "mimeType")String mimeType);
}
