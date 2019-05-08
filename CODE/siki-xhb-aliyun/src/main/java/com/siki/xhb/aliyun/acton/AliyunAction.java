package com.siki.xhb.aliyun.acton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siki.xhb.aliyun.config.OssProperties;
import com.siki.xhb.aliyun.utils.OSSUtils;
import com.siki.xhb.aliyun_i.action.AliyunActionI;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class AliyunAction implements AliyunActionI {

    @Autowired
    OssProperties ossProperties;

    @Override
    public ResObject getOssSignature() {
        log.info(ossProperties.getAccessId());
        ResObject responseObject = new ResObject();
        OSSUtils ossUtils = new OSSUtils();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        response.setContentType("text/plain;charset=UTF-8");
        Enumeration<String> keyList = request.getParameterNames();
        Map<String,String> paramMap = new HashMap<>();
        while (keyList.hasMoreElements()){
            String key = keyList.nextElement();
            paramMap.put(key,request.getParameter(key));
        }
        String callback = paramMap.remove("callback");
        responseObject.setCode(1);
        responseObject.setMessage("请求成功！");
        ObjectMapper mapper = new ObjectMapper();
        JSONObject ossSign = ossUtils.getOSSSign(callback, paramMap,ossProperties);
        responseObject.setRes(ossSign);
        return responseObject;
    }

    @Override
    public ResObject ossServiceCallback(@RequestParam(value = "key") String key) {
        ResObject responseObject = new ResObject();
        OSSUtils ossUtils = new OSSUtils();
        InputStream is = ossUtils.getOSSFileStream(key,ossProperties);
        try {
            if(is != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while (true){
                    String line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    log.info("\n",line);
                }
                is.close();
            }
        }catch (Exception e){

        }
        return responseObject;
    }
}
