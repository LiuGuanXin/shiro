package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.dao.RoleResourcesMapper;
import com.xxl.job.admin.dao.UserRoleMapper;
import com.xxl.job.admin.model.RoleResources;
import com.xxl.job.admin.service.RoleResourcesService;
import com.xxl.job.admin.shiro.MyCustomRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangqj on 2017/4/26.
 */
@Service
public class RoleResourcesServiceImpl implements RoleResourcesService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourcesMapper roleResourcesMapper;
    /*@Resource
    private ShiroService shiroService;*/
//    @Autowired
//    private MyCustomRealm myCustomRealm;

    @Override
    //更新权限
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    //@CacheEvict(cacheNames="resources", allEntries=true)
    public void addRoleResources(RoleResources roleResources) {
        //删除
        roleResourcesMapper.deleteResourcesByRole(roleResources.getRoleId());
        //添加
        if(!StringUtils.isEmpty(roleResources.getResourcesId())){
            String[] resourcesArr = roleResources.getResourcesId().split(",");
            for(String resourcesId:resourcesArr ){
                RoleResources r = new RoleResources();
                r.setRoleId(roleResources.getRoleId());
                r.setResourcesId(resourcesId);
                roleResourcesMapper.insertRoleResources(r);
            }
        }

//        List<Integer> userIds= userRoleMapper.findUserIdByRoleId(roleResources.getRoleId());
        //更新当前登录的用户的权限缓存
//        myCustomRealm.clearUserAuthByUserId(userIds);


    }

    @Override
    public void deleteResourcesByRole(Integer roleId) {
        roleResourcesMapper.deleteResourcesByRole(roleId);
    }
}
