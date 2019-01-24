package com.xxl.job.admin.service;

import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Role;


import java.util.List;

public interface RoleService {

    public List<Role> queryRoleListWithSelected(Integer uid);

    public Role queryRole(Integer id);

    public List<Role> queryAll();

    PageInfo<Role> selectByPage(int start, int length);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleId
     */
    public void delRole(Integer roleId);

    public String addRole(Role role);
}
