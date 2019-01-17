package com.xxl.job.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.dao.RoleMapper;
import com.xxl.job.admin.dao.RoleResourcesMapper;
import com.xxl.job.admin.model.Role;
import com.xxl.job.admin.model.RoleResources;
import com.xxl.job.admin.service.RoleResourcesService;
import com.xxl.job.admin.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourcesService roleResourcesService;

    @Override
    public List<Role> queryRoleListWithSelected(Integer uid) {
        return roleMapper.queryRoleListWithSelected(uid);
    }
    public Role queryRole(Integer id){
        return roleMapper.queryRole(id);
    }
    @Override
    public PageInfo<Role> selectByPage(Role role, int start, int length) {
        int page = start/length + 1;
        //分页查询
        PageHelper.startPage(page, length);
        List<Role> rolesList = roleMapper.selectRolesList(role);
        return new PageInfo<>(rolesList);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delRole(Integer roleId) {
        //删除角色
        roleMapper.deleteRole(roleId);
        //删除角色资源
        roleResourcesService.deleteResourcesByRole(roleId);
    }

    @Override
    public String addRole(Role role) {
        Role role1 = roleMapper.selectRole(role.getId());
        if (role1 != null)
            return "角色已存在";
        try {
            roleMapper.addRole(role);
            return "添加角色成功";
        }catch (Exception e){
            e.printStackTrace();
            return "添加角色失败";
        }

    }
}
