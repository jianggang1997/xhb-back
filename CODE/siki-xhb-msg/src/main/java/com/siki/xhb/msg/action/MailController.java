package com.siki.xhb.msg.action;


import com.siki.xhb.msg.service.MailService;
import com.siki.xhb.msgI.action.MailControllerInterface;
import com.siki.xhb.vo.constant.DesKey;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.siki.xhb.vo.model.ResObject;
import com.siki.xhb.vo.constant.RsqCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class MailController implements MailControllerInterface {

    @Autowired
    private MailService mailService;

    @Autowired
    private Configuration configuration;


    @Override
    public ResObject sendMailValidCode(@RequestParam(value = "to")String to){
        ResObject res = new ResObject();
        Map<String,String> param = new HashMap<>(1);
        String validCode = String.valueOf((int)((Math.random()*9+1)*100000));
        param.put("validCode",validCode);
        HttpServletResponse response = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getResponse();
        try {
            Template template = configuration.getTemplate("validCodeTemplate.flt");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template,param);
            mailService.sendTemplateMail(to,"登陆验证码",content);
            log.info("-------------->发送到：{}，发送的验证码为：{}",to,validCode);
            response.addHeader(DesKey.VALID_CODE_TO_RESPONSE_HEAD,to);
            response.addHeader(DesKey.VALID_CODE_RESPONSE_HEAD,validCode);
            res.setCode(RsqCode.RESPONSE_SUCCESS);
            res.setMessage("邮箱验证码发送成功！");
        } catch (Exception e) {
            res.setCode(RsqCode.RESPONSE_FAIL);
            res.setMessage("邮箱验证码发送失败！");
            e.printStackTrace();
        }
        return res;
    }

}
