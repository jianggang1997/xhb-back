package com.siki.xhb.aliyun.utils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.siki.xhb.aliyun.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author jianggang
 * @Date 2019/5/8 13:14:13
 * @Description
 */

@Slf4j
public class OSSUtils {

    /**
     *获取OSS数字签名
     * @return
     */
    public  JSONObject getOSSSign(String callback, Map<String,String> paramMap,OssProperties ossProperties){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("property","property-value");

        JSONObject signJsonObject = null;

        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint(),ossProperties.getAccessId(),ossProperties.getAccessKey());

        try {
            Date expiration = new Date(System.currentTimeMillis() + 30 * 1000);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0,104857600);
//        policyConditions.addConditionItem(MatchMode.StartWith,PolicyConditions.COND_KEY,dir);
            String postPolicy = ossClient.generatePostPolicy(expiration,policyConditions);

            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String,String> respMap = new LinkedHashMap<String,String>();

            /* 拼接回调函数参数 */
            Iterator<String> paramKey = paramMap.keySet().iterator();
            String paramUrl = "";
            while(paramKey.hasNext()){
                String key = paramKey.next();
                paramUrl += '&' + key + '=' + paramMap.get(key);
            }
            if(paramUrl.length()>0){
                paramUrl = paramUrl.substring(1);
            }

            respMap.put("accessid",ossProperties.getAccessId());
            respMap.put("host",ossProperties.getHost());
            respMap.put("policy",encodedPolicy);
            respMap.put("signature",postSignature);
            respMap.put("dir",ossProperties.getDir());

            Map<String,Object> callbackMap = new HashMap<String,Object>();
            callbackMap.put("callbackUrl",ossProperties.getCallbackUrl());
            callbackMap.put("callbackBody",paramUrl);
            callbackMap.put("callbackBodyType","application/x-www-form-urlencoded");
            byte[] callbackBytes = JSONObject.fromObject(callbackMap).toString().getBytes("utf-8");
            String callbackEncode = BinaryUtil.toBase64String(callbackBytes);
            respMap.put("callback",callbackEncode);
            respMap.put("expire",String.valueOf(expiration.getTime()));

            signJsonObject = JSONObject.fromObject(respMap);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
        return signJsonObject;
    }

    /**
     * 读取文件流
     * @return
     */
    public  InputStream getOSSFileStream(String key,OssProperties ossProperties){
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint(),ossProperties.getAccessId(),ossProperties.getAccessKey());
        try {
            OSSObject ossObject = ossClient.getObject(ossProperties.getBucket(),key);
            return ossObject.getObjectContent();
        }catch (Exception e){
            return null;
        }
    }
}
