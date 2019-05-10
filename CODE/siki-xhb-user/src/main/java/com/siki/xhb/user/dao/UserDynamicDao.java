package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.UserDynamicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther jianggang
 * @Date 2019/5/8 18:44:07
 * @Description
 */

@Mapper
public interface UserDynamicDao {

    /**
     * 插入用户动态
     * @param userDynamicInfo
     * @return
     */
    int insertUserDynamic(@Param(value = "userDynamicInfo")UserDynamicInfo userDynamicInfo);


    /**
     * 查找动态
     * @param userDynamicInfo
     * @param start
     * @param pageSize
     * @return
     */
    List<UserDynamicInfo> selectUserDynamic(@Param(value = "userDynamicInfo")UserDynamicInfo userDynamicInfo,
                                            @Param(value = "start")int start,
                                            @Param(value = "pageSize")int pageSize);

}
