package com.siki.xhb.user.service.imp;

import com.siki.xhb.user.dao.RegisterInfoDao;
import com.siki.xhb.user.model.RegisterInfo;
import com.siki.xhb.user.service.UserOperationService;
import com.siki.xhb.userI.utils.Md5Util;
import com.siki.xhb.userI.utils.SidUtil;
import com.siki.xhb.userI.vo.ModifyPassReq;
import com.siki.xhb.userI.vo.UserLoginReq;
import com.siki.xhb.userI.vo.UserRaegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserOperationServiceImp implements UserOperationService {

    @Autowired
    private RegisterInfoDao registerInfoDao;

    @Override
    public boolean userRegister(UserRaegisterReq userRaegisterReq,String ip) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(userRaegisterReq.getAccount());
        registerInfo.setPwd_md5(Md5Util.encrypt32(userRaegisterReq.getPassword()));
        registerInfo.setSid(SidUtil.generator());
        registerInfo.setReg_ip(ip);
        String regMail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        if(Pattern.matches(regMail,userRaegisterReq.getAccount())){
            registerInfo.setReg_type(1);
        }else {
            registerInfo.setReg_type(2);
        }
        registerInfo.setInfo_status(0);

        return registerInfoDao.insertRegisterInfo(registerInfo)>0;
    }

    @Override
    public String validateUserLogin(UserLoginReq userLoginReq) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(userLoginReq.getAccount());
        List<RegisterInfo> infos = registerInfoDao.selectRegisterInfo(registerInfo,0,1);
        if(infos.size() == 1){
            if(Md5Util.encrypt32(userLoginReq.getPassword()).equals(infos.get(0).getPwd_md5())
                    && userLoginReq.getAccount().equals(infos.get(0).getAccount())){
                return infos.get(0).getSid();
            }else{
                return "2";
            }
        }else {
            return "1";
        }
    }

    @Override
    public boolean userMidifyPass(ModifyPassReq modifyPassReq) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(modifyPassReq.getAccount());
        registerInfo.setPwd_md5(Md5Util.encrypt32(modifyPassReq.getNewPass()));
        return  registerInfoDao.updateRegisterInfo(registerInfo)>0;
    }
}
