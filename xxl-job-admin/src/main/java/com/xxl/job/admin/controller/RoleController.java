package com.xxl.job.admin.controller;


import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Role;
import com.xxl.job.admin.model.RoleResources;
import com.xxl.job.admin.service.RoleResourcesService;
import com.xxl.job.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/26.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleServiceImpl;
    private final RoleResourcesService roleResourcesServiceImpl;

    @Autowired
    public RoleController(RoleService roleServiceImpl, RoleResourcesService roleResourcesServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
        this.roleResourcesServiceImpl = roleResourcesServiceImpl;
    }

    @RequestMapping
    public  Map<String,Object> getAll(Role role, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();
        PageInfo<Role> pageInfo = roleServiceImpl.selectByPage(role, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping("/rolesWithSelected")
    public List<Role> rolesWithSelected(Integer uid){
        return roleServiceImpl.queryRoleListWithSelected(uid);
    }

    //分配角色
    @RequestMapping("/saveRoleResources")
    public String saveRoleResources(RoleResources roleResources){
        if(StringUtils.isEmpty(roleResources.getRoleId()))
            return "error";
        try {
            roleResourcesServiceImpl.addRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(Role role) {
        return roleServiceImpl.addRole(role);

    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            roleServiceImpl.delRole(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }



}
