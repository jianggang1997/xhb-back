package com.siki.xhb.user.dao;

import com.siki.xhb.user.model.UserImgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther jianggang
 * @Date 2019/5/8 18:37:17
 * @Description
 */

@Mapper
public interface UserImgsDao {


    /**
     * 插入用户图片
     * @param userImgInfo
     * @return
     */
    int insertUserImg(@Param(value = "userImgInfo") UserImgInfo userImgInfo);

    /**
     * 查询用户图片
     * @param userImgInfo
     * @param start
     * @param pageSize
     * @return
     */
    List<UserImgInfo> selectUserImgs(@Param(value = "userImgInfo") UserImgInfo userImgInfo,
                                     @Param(value = "start") int start,
                                     @Param(value = "pageSize") int pageSize);

    /**
     * 根据sid集合查询id集合
     * @param sids
     * @return
     */
    List<Integer> selectImgIdBySids(@Param(value = "sids") List<String> sids);
}
