package com.siki.xhb.user.dao;


import com.siki.xhb.user.model.RegisterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegisterInfoDao {

    /**
     * 查询注册信息表
     * @param info
     * @param start
     * @param pageSize
     * @return
     */
    List<RegisterInfo> selectRegisterInfo(@Param("info") RegisterInfo info,
                            @Param("start") int start,
                            @Param("pageSize") int pageSize);

    /**
     * 插入注册信息表
     * @param info
     * @return
     */
    int insertRegisterInfo(@Param("info") RegisterInfo info);

    /**
     * 更新注册信息表
     * @param info
     * @return
     */
    int updateRegisterInfo(@Param("info") RegisterInfo info);
}
