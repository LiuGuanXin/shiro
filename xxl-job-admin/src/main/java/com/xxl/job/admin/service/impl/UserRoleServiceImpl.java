package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.dao.UserRoleMapper;
import com.xxl.job.admin.model.UserRole;
import com.xxl.job.admin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqj on 2017/4/26.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
//    @Autowired
//    private MyShiroRealm myShiroRealm;

    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void addUserRole(UserRole userRole) {
        //删除
        userRoleMapper.delUserRole(userRole.getUserId());
        //添加
        String[] roleids = userRole.getRoleId().split(",");
        for (String roleId : roleids) {
            UserRole u = new UserRole();
            u.setUserId(userRole.getUserId());
            u.setRoleId(roleId);
            userRoleMapper.insertUserRole(userRole);
        }
//        //更新当前登录的用户的权限缓存
//        List<Integer> userid = new ArrayList<Integer>();
//        userid.add(userRole.getUserid());
//        myShiroRealm.clearUserAuthByUserId(userid);
    }

    @Override
    public void delUserRole(Integer userId) {
        userRoleMapper.delUserRole(userId);
    }

    @Override
    public List<Integer> getRoleByUser(Integer userId) {
        return userRoleMapper.getRoleIdByUser(userId);
    }
}
