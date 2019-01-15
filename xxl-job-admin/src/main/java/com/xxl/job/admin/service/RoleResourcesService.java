package com.xxl.job.admin.service;


import com.xxl.job.admin.model.RoleResources;

/**
 * Created by yangqj on 2017/4/26.
 */
public interface RoleResourcesService {
    public void addRoleResources(RoleResources roleResources);

    public void deleteResourcesByRole(Integer roleId);

}
