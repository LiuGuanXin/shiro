package com.xxl.job.admin.service;

import com.xxl.job.admin.model.User;
import com.github.pagehelper.PageInfo;
/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:07
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public interface UserService {

    public User selectByUsername(String username);

    public PageInfo<User> selectByPage(User user, int start, int length);

    public void delUser(Integer userid);

    public String addUser(User user);
}
