package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.dao.UserMapper;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
