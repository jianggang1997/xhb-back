package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.DynamicShareInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther jianggang
 * @Date 2019/5/9 13:31:36
 * @Description
 */

@Mapper
public interface DynamicShareInfoDao {

    /**
     * 插入动态分享
     * @param dynamicShareInfo
     * @return
     */
    int insertShareInfo(@Param(value = "dynamicShareInfo")DynamicShareInfo dynamicShareInfo);

    /**
     * 动态分享次数查询
     * @param dynamicId
     * @return
     */
    int countShareByDyId(@Param(value = "dynamicId")Integer dynamicId);
}
