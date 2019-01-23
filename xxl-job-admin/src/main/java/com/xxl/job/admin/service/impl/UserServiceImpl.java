package com.xxl.job.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.dao.ResourcesMapper;
import com.xxl.job.admin.dao.UserMapper;
import com.xxl.job.admin.dao.UserRoleMapper;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.model.UserRole;
import com.xxl.job.admin.service.UserRoleService;
import com.xxl.job.admin.service.UserService;

import com.xxl.job.admin.service.UserTriggerService;
import com.xxl.job.admin.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:08
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTriggerService UserTriggerServiceImpl;
    @Autowired
    private UserRoleService userRoleServiceImpl;
    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public PageInfo<User> selectByPage(User user, int start, int length) {
        int page = start/length+1;

        //分页查询
        PageHelper.startPage(page, length);
        List<User> userList = userMapper.selectByPage(user);
        return new PageInfo<>(userList);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delUser(Integer userId) {
        //删除用户表
        userMapper.delUser(userId);
        //删除用户角色表
        userRoleServiceImpl.delUserRole(userId);
        UserTriggerServiceImpl.deleteByUser(userId);
    }

    @Override
    public String addUser(User user) {
        User u = userMapper.selectByUsername(user.getUsername());
        if(u != null)
            return "用户名已存在";
        try {
            user.setEnable(1);
//            PasswordHelper passwordHelper = new PasswordHelper();
//            passwordHelper.encryptPassword(user);
            userMapper.addUser(user);
            return "添加用户成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加用户失败";
        }
    }
}
