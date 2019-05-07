package com.siki.xhb.user.action;


import com.google.common.base.Preconditions;
import com.siki.xhb.user.service.UserOperationService;
import com.siki.xhb.userI.action.UserOperationI;
import com.siki.xhb.userI.vo.ModifyPassReq;
import com.siki.xhb.userI.vo.UserLoginReq;
import com.siki.xhb.userI.vo.UserRaegisterReq;
import com.siki.xhb.vo.constant.DesKey;
import com.siki.xhb.vo.constant.RsqCode;
import com.siki.xhb.vo.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class UserOperationController implements UserOperationI {

    @Autowired
    private UserOperationService userOperationService;


    @Override
    public ResObject userRegister(@RequestBody UserRaegisterReq userRaegisterReq) {
        log.info("------------用户注册：{}",userRaegisterReq.toString());
        Preconditions.checkNotNull(userRaegisterReq.getAccount(),"账号不能为空");
        Preconditions.checkNotNull(userRaegisterReq.getPassword(),"密码不能为空");
        String regMail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        String regTel = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
        Preconditions.checkArgument(Pattern.matches(regMail,userRaegisterReq.getAccount())||
                Pattern.matches(regTel,userRaegisterReq.getAccount()),"账号不符合要求");
        ResObject res = new ResObject();
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        String ip = request.getHeader(DesKey.REQUEST_REMOTE_ADDR);
        if(userOperationService.userRegister(userRaegisterReq,ip)){
            res.setCode(RsqCode.RESPONSE_SUCCESS);
            res.setMessage("注册成功！");
            return res;
        }
        res.setCode(RsqCode.RESPONSE_FAIL);
        res.setMessage("注册失败！");
        return res;
    }


    @Override
    public ResObject userLogin(@RequestBody UserLoginReq userLoginReq) {
        log.info("-----------------用户登录：{}",userLoginReq.toString());
        Preconditions.checkNotNull(userLoginReq.getAccount(),"账号不能为空");
        Preconditions.checkNotNull(userLoginReq.getPassword(),"密码不能为空");
        String regMail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        String regTel = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
        Preconditions.checkArgument(Pattern.matches(regMail,userLoginReq.getAccount())||
                Pattern.matches(regTel,userLoginReq.getAccount()),"账号不符合要求");
        HttpServletResponse response = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getResponse();
        ResObject res = new ResObject();
        String result = userOperationService.validateUserLogin(userLoginReq);
        if("2".equals(result)){
            res.setCode(RsqCode.USER_PASS_ERROR);
            res.setMessage("密码错误！");
            return res;
        }else if("1".equals(result)){
            res.setCode(RsqCode.USER_NOT_EXIST);
            res.setMessage("用户不存在！");
            return res;
        }
        response.addHeader(DesKey.USER_LOGIN_RESPONSE_HEAD_SID,result);
        response.addHeader(DesKey.USER_LOGIN_RESPONSE_HRAD_ACCOUNT,userLoginReq.getAccount());
        res.setCode(RsqCode.RESPONSE_SUCCESS);
        res.setMessage("登录成功！");
        return res;
    }

    @Override
    public ResObject modifyPass(@RequestBody ModifyPassReq modifyPassReq) {
        log.info("----------------->修改密码{}",modifyPassReq.toString());
        Preconditions.checkNotNull(modifyPassReq.getAccount(),"账号不能为空");
        Preconditions.checkNotNull(modifyPassReq.getNewPass(),"密码不能为空");
        String regMail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        String regTel = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
        Preconditions.checkArgument(Pattern.matches(regMail,modifyPassReq.getAccount())||
                Pattern.matches(regTel,modifyPassReq.getAccount()),"账号不符合要求");
        ResObject res = new ResObject();
        if(userOperationService.userMidifyPass(modifyPassReq)){
            res.setCode(RsqCode.RESPONSE_SUCCESS);
            res.setMessage("修改成功！");
            return res;
        }
        res.setCode(RsqCode.RESPONSE_FAIL);
        res.setMessage("修改失败！");
        return res;
    }

    @Override
    public ResObject isExistUser(String accout) {
        log.info("----------------->检验用户名是否存在：{}",accout);
        ResObject res = new ResObject();
        Preconditions.checkArgument(!StringUtils.isBlank(accout),"账号不能为空");
        boolean isExist = userOperationService.checkisExistAccout(accout);
        if(isExist){
            res.setCode(RsqCode.RESPONSE_SUCCESS);
            res.setMessage("账号存在");
            return res;
        }
        res.setCode(RsqCode.USER_NOT_EXIST);
        res.setMessage("账号不存在");
        return res;
    }
}
