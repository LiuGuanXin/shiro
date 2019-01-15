package com.xxl.job.admin.dao;

import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.sqlProvider.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    @SelectProvider(type = UserProvider.class,method = "selectByPage")
    public List<User> selectByPage(@Param("user") User user);
    @Delete("delete from user where id = #{userId}")
    public void delUser(Integer userId);
    @Insert("insert into user (username,password) values (#{username},#{password})")
    public void addUser(User user);
}
