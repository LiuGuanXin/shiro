package com.xxl.job.admin.dao;

import com.xxl.job.admin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 08:45
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    public User selectByUsername(@Param("username") String username);
}
