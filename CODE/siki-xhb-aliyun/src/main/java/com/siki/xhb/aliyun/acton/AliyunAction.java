package com.siki.xhb.aliyun.acton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.siki.xhb.aliyun.config.OssProperties;
import com.siki.xhb.aliyun.utils.OSSUtils;
import com.siki.xhb.aliyun.vo.ImageUploadInfo;
import com.siki.xhb.aliyun_i.action.AliyunActionI;
import com.siki.xhb.vo.constant.RsqCode;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianggang
 * @Date 2019/5/8 13:12:00
 * @Description
 */

@Slf4j
@RestController
@CrossOrigin
public class AliyunAction implements AliyunActionI {

    @Autowired
    OssProperties ossProperties;

    @Override
    public ResObject getOssSignature(@RequestParam(value = "filePrefix") String filePrefix) {
        ResObject responseObject = new ResObject();
        OSSUtils ossUtils = new OSSUtils();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        response.setContentType("text/plain;charset=UTF-8");
        Enumeration<String> keyList = request.getParameterNames();
        Map<String,String> paramMap = new HashMap<>();
        while (keyList.hasMoreElements()){
            String key1 = keyList.nextElement();
            paramMap.put(key1,request.getParameter(key1));
        }
        String callback = paramMap.remove("callback");
        responseObject.setCode(RsqCode.RESPONSE_SUCCESS);
        responseObject.setMessage("请求成功！");
        ObjectMapper mapper = new ObjectMapper();
        JSONObject ossSign = ossUtils.getOSSSign(callback, paramMap,ossProperties,filePrefix);
        responseObject.setRes(ossSign);
        return responseObject;
    }

    @Override
    public ResObject ossServiceCallback(@RequestParam(value = "filename")String filename,
                                        @RequestParam(value = "size")String size,
                                        @RequestParam(value = "mimeType")String mimeType) {
        Preconditions.checkArgument(!StringUtils.isBlank(filename),"文件名不能为空");
        Preconditions.checkArgument(!StringUtils.isBlank(size),"文件大小为空");
        Preconditions.checkArgument(!StringUtils.isBlank(mimeType),"文件类型为空");
        ImageUploadInfo info = new ImageUploadInfo();
        info.setFilename(filename);
        info.setSize(size);
        info.setMimeType(mimeType);
        ResObject responseObject = new ResObject();
        responseObject.setCode(RsqCode.RESPONSE_SUCCESS);
        responseObject.setMessage("图片上传成功！");
        responseObject.setRes(info);
        return responseObject;
    }
}
