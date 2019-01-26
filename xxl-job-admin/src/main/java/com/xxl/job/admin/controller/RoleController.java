package com.xxl.job.admin.controller;


import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Role;
import com.xxl.job.admin.model.RoleResources;
import com.xxl.job.admin.service.RoleResourcesService;
import com.xxl.job.admin.service.RoleService;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/26.
 */
@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleServiceImpl;
    private final RoleResourcesService roleResourcesServiceImpl;

    @Autowired
    public RoleController(RoleService roleServiceImpl, RoleResourcesService roleResourcesServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
        this.roleResourcesServiceImpl = roleResourcesServiceImpl;
    }

//    @RequiresRoles("1")
    @RequestMapping
    public String getAll(
                         @RequestParam(required = false, defaultValue = "0") int start,
                         @RequestParam(required = false, defaultValue = "10") int length){
        return "role/role.index";
    }
    @ResponseBody
    @RequestMapping("/pageList")
    public  Map<String,Object> getList(
                                      @RequestParam(required = false, defaultValue = "0") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();
        PageInfo<Role> pageInfo = roleServiceImpl.selectByPage(start, length);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
    @ResponseBody
    @RequestMapping("/rolesWithSelected")
    public List<Role> rolesWithSelected(Integer uid){
        return roleServiceImpl.queryRoleListWithSelected(uid);
    }

    //分配角色
    @ResponseBody
    @RequestMapping("/saveRoleResources")
    public String saveRoleResources(RoleResources roleResources){
        if (!SecurityUtils.getSubject().isPermitted("/roles/saveRoleResources")){
            return "noPer";
        }
        if(StringUtils.isEmpty(roleResources.getRoleId()))
            return "fail";
        try {
            roleResourcesServiceImpl.addRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @ResponseBody
    @RequestMapping(value = "/add")
    public ReturnT<String> add(Role role) {
        return new ReturnT<String>(roleServiceImpl.addRole(role));

    }
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ReturnT<String> delete(Integer id){
        try{
            roleServiceImpl.delRole(id);
            return ReturnT.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }



}
