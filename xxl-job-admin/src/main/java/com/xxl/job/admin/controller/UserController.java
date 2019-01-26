package com.xxl.job.admin.controller;

import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.model.Role;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.model.UserRole;
import com.xxl.job.admin.service.RoleService;
import com.xxl.job.admin.service.UserRoleService;
import com.xxl.job.admin.service.UserService;
import com.xxl.job.admin.util.PasswordHelper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-15 16:34
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Controller
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
//
//    @Autowired
//    private RoleService roleServiceImpl;

@ResponseBody
@RequestMapping("/pageList")
public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                    @RequestParam(required = false, defaultValue = "10") int length,
                                    String id, String username, String enable) {
    User user = new User();
    if (!"".equals(id)){
        user.setId(Integer.valueOf(id));
    }
    if (!"2".equals(enable)){
        user.setEnable(Integer.valueOf(enable));
    }
    user.setUsername(username);
    Map<String,Object> map = new HashMap<>();
    PageInfo<User> pageInfo = userService.selectByPage(user, start, length);
//    List<Role> list = roleServiceImpl.queryAll();
    System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
    map.put("recordsTotal",pageInfo.getTotal());
    map.put("recordsFiltered",pageInfo.getTotal());
//    map.put("roleList",list);
    map.put("data", pageInfo.getList());
    return map;
}


//    @RequiresRoles("1")
    @RequestMapping
    public String getAll(){
        return "user/user.index";
    }
    /**
     * 保存用户角色
     * @param userRole 用户角色
     *  	  此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveUserRoles")
    public String saveUserRoles(UserRole userRole){
        if (!SecurityUtils.getSubject().isPermitted("/users/saveUserRoles")){
            return "noPer";
        }
        if(StringUtils.isEmpty(userRole.getUserId()))
            return "";
        try {
            userRoleService.addUserRole(userRole);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public ReturnT<String> add(User user) {
        if (!SecurityUtils.getSubject().isPermitted("/users/add")){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足");
        }
        return new ReturnT<String>(userService.addUser(user));
    }
    @ResponseBody
//    @RequiresPermissions("users/delete")
    @RequestMapping(value = "/delete")
    public ReturnT<String> delete(Integer id){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (id.equals(user.getId())){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足");
        }
        if (!SecurityUtils.getSubject().isPermitted("/users/delete")){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足");
        }
        try{
            userService.delUser(id);
            return ReturnT.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }

}
