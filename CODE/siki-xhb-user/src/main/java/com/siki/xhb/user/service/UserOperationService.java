package com.siki.xhb.user.service;

import com.siki.xhb.userI.vo.ModifyPassReq;
import com.siki.xhb.userI.vo.UserLoginReq;
import com.siki.xhb.userI.vo.UserRaegisterReq;

public interface UserOperationService {

    /**
     * 用户注册
     * @param userRaegisterReq
     * @return
     */
    boolean userRegister(UserRaegisterReq userRaegisterReq,String ip);

    /**
     * 验证用户登录
     * @param userLoginReq
     * @return 1-用户不存在 2-密码错误 sid-验证成功
     */
    String validateUserLogin(UserLoginReq userLoginReq);

    /**
     * 用户修改密码
     * @param modifyPassReq
     * @return
     */
    boolean userMidifyPass(ModifyPassReq modifyPassReq);

    /**
     * 检查账户是否存在
     * @param accout
     * @return
     */
    boolean checkisExistAccout(String accout);
}
