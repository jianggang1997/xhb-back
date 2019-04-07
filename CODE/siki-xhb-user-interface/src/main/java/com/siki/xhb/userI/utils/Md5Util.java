package com.siki.xhb.userI.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String encrypt32(String target){
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(target.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for(int i= 0 ; i < md5Bytes.length; i++){
                int val = ((int)md5Bytes[i]&0xff);
                if (val < 16){
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            target = hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }
}
