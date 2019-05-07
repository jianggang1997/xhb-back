package com.siki.xhb.userI.action;

import com.siki.xhb.userI.vo.ModifyPassReq;
import com.siki.xhb.userI.vo.UserLoginReq;
import com.siki.xhb.userI.vo.UserRaegisterReq;
import com.siki.xhb.vo.model.ResObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "siki-xhb-user")
@Api(value = "用户操作接口")
public interface UserOperationI {


    @PostMapping(value = "/register.do")
    @ApiOperation(value = "用户注册")
    ResObject userRegister(@RequestBody UserRaegisterReq userRaegisterReq);

    @PostMapping(value = "/login.do")
    @ApiOperation(value = "用户登录")
    ResObject userLogin(@RequestBody UserLoginReq userLoginReq);

    @PostMapping(value = "/modifyPass.do")
    @ApiOperation(value = "修改账号密码")
    ResObject modifyPass(@RequestBody ModifyPassReq modifyPassReq);

    @PostMapping(value = "/isExistAccout.do")
    @ApiOperation(value = "检验账号是否存在")
    ResObject isExistUser(@RequestParam("accout") String accout);
}
