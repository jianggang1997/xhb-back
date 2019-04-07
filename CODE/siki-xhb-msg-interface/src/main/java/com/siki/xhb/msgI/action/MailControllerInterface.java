package com.siki.xhb.msgI.action;


import com.siki.xhb.vo.model.ResObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "邮件接口")
@FeignClient(value = "siki-xhb-msg")
public interface MailControllerInterface {

    @ApiOperation(value = "发送邮件验证")
    @PostMapping(value = "/sendMailValidCode.do")
    ResObject sendMailValidCode(@RequestParam(value = "to")String to);

}
