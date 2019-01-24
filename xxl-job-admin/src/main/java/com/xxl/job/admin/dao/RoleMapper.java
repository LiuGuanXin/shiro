package com.xxl.job.admin.dao;

import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-14 11:56
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface RoleMapper {

    @Select("SELECT r.id,r.roleDesc,(CASE WHEN (SELECT ur.roleId FROM user_role " +
            "ur WHERE ur.userId= #{uid} AND ur.roleId = r.id) THEN 1 ELSE 0 END) AS selected FROM role r")
    public List<Role> queryRoleListWithSelected(Integer uid);

    @Select("SELECT * FROM role")
    public List<Role> selectRolesList();

    @Select("SELECT * FROM role WHERE id = #{id}")
    public Role selectRole(Integer id);
    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleId
     */
    @Delete("delete from role WHERE id = #{roleId}")
    public void deleteRole(Integer roleId);
    @Insert("insert into role (id,roleDesc) values(#{id},#{roleDesc})")
    public void addRole (Role role);

    @Select("select * from role where id = #{id}")
    public Role queryRole(Integer id);
}
