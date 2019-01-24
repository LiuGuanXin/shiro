package com.xxl.job.admin.service;


import com.xxl.job.admin.model.UserRole;

import java.util.List;

/**
 * Created by yangqj on 2017/4/26.
 */
public interface UserRoleService{

    public void addUserRole(UserRole userRole);

    public void delUserRole(Integer userId);

    public List<Integer> getRoleByUser(Integer userId);

}
