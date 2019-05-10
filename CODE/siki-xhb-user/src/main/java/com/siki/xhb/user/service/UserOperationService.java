package com.siki.xhb.user.service;

import com.siki.xhb.userI.vo.*;

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

    /**
     * 发布动态
     * @param publishDynamicReq
     * @param userId
     * @return
     */
    boolean publishDynamic(PublishDynamicReq publishDynamicReq,String userId);

    /**
     * 踩动态
     * @param stampDynamicReq
     * @param userId
     * @return
     */
    boolean stampDynamic(StampDynamicReq stampDynamicReq,String userId);

    /**
     * 分享动态
     * @param shareDynamicReq
     * @param userId
     * @return
     */
    boolean shareDynamic(ShareDynamicReq shareDynamicReq,String userId);

    /**
     * 评论动态
     * @param commentDynamicReq
     * @param userId
     * @return
     */
    boolean CommentDynamic(CommentDynamicReq commentDynamicReq,String userId);

    /**
     * 回复动态评论
     * @param replyCommentReq
     * @param userId
     * @return
     */
    boolean ReplyDynamicComment(ReplyCommentReq replyCommentReq,String userId);
}
