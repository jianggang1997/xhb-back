package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.CommentReplyInfo;
import com.siki.xhb.user.model.DynamicCommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther jianggang
 * @Date 2019/5/9 13:55:06
 * @Description
 */
@Mapper
public interface DynamicCommentDao {

    int insertComment(@Param(value = "dynamicCommentInfo") DynamicCommentInfo dynamicCommentInfo);

    int countCommentByDyId(@Param(value = "dynamicId") Integer dynamicId);
}
