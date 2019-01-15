package com.xxl.job.admin.dao;

import com.xxl.job.admin.model.RoleResources;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-14 11:57
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface RoleResourcesMapper {
    @Delete("delete from role_resources where roleId = #{roleId}")
    public void deleteResourcesByRole(Integer roleId);
    @Insert("insert into role_resources (roleId,resourcesId) values (#{roleId},#{resourcesId})")
    public void insertRoleResources(RoleResources roleResources);
}
