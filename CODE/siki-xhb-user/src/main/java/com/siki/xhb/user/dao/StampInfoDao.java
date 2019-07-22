package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.DynamicStampInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther jianggang
 * @Date 2019/5/9 12:59:18
 * @Description
 */

@Mapper
public interface StampInfoDao {


    /**
     * 插入动态踩信息
     * @param dynamicStampInfo
     * @return
     */
    int insertStampInfo(@Param(value = "")DynamicStampInfo dynamicStampInfo);

    /**
     * 根据动态id查询动态踩总数
     * @param dynamicId
     * @return
     */
    int queryStampCountByDyId(@Param("dynamicId") Integer dynamicId);
}
