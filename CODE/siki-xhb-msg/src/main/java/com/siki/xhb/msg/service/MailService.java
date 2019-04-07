package com.siki.xhb.msg.service;

public interface MailService {

    /**
     * 发送验证码邮件
     * @param to 发送目标邮件地址
     * @param subject
     * @param content 发送验证
     * @return
     */
    boolean sendTemplateMail(String to, String subject, String validCode);

}
