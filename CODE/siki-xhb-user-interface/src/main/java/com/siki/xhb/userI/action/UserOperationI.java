package com.siki.xhb.userI.action;

import com.siki.xhb.userI.vo.*;
import com.siki.xhb.vo.model.ResObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping(value = "publishDynamic,do")
    @ApiOperation(value = "发布动态")
    ResObject publishDynamic(@RequestBody PublishDynamicReq publishDynamicReq,
                             @RequestHeader(value = "userId") String userId);

    @PostMapping(value = "queryDynamic.do")
    @ApiOperation(value = "查询校园动态")
    ResObject queryDynamic(@RequestBody QueryDynamicReq queryDynamicReq,
                           @RequestHeader(value = "userId")String userId);

    @PostMapping(value = "stampDynamic.do")
    @ApiOperation(value = "踩动态")
    ResObject stampDynamic(@RequestBody StampDynamicReq stampDynamicReq,
                           @RequestHeader(value = "userId")String userId);

    @PostMapping(value = "shareDynamic.do")
    @ApiOperation(value = "分享动态")
    ResObject shareDynamic(@RequestBody ShareDynamicReq shareDynamicReq,
                           @RequestHeader(value = "userId")String userId);

    @PostMapping(value = "commentDynamic.do")
    @ApiOperation(value = "评论动态")
    ResObject commentDynamic(@RequestBody CommentDynamicReq commentDynamicReq,
                             @RequestHeader(value = "userId")String userId);

    @PostMapping(value = "replyComment.do")
    @ApiOperation(value = "回复评论")
    ResObject replyComment(@RequestBody ReplyCommentReq replyCommentReq,
                           @RequestHeader(value = "userId")String userId);
}
