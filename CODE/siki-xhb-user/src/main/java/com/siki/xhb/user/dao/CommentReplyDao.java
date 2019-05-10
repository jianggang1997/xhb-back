package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.CommentReplyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther jianggang
 * @Date 2019/5/9 14:13:30
 * @Description
 */

@Mapper
public interface CommentReplyDao {

    /**
     *
     * @param commentReplyInfo
     * @return
     */
    int insertCommentReply(@Param(value = "commentReplyInfo") CommentReplyInfo commentReplyInfo);

}
