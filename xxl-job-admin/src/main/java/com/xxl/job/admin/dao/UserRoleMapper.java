package com.xxl.job.admin.dao;


import com.xxl.job.admin.model.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-14 11:26
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface UserRoleMapper {
    @Delete("delete from user_role where userId = #{userId}")
    public void delUserRole(Integer userId);

    @Insert("insert into user_role (userId,roleId) values (#{userId},#{roleId})")
    public void insertUserRole(UserRole userRole);
    @Select("select roleId from user_role where userId = #{userId}")
    public List<Integer> getRoleIdByUser(Integer roleId);
}
