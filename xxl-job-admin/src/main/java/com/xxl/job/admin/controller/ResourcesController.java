package com.xxl.job.admin.controller;


import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Resources;
import com.xxl.job.admin.service.ResourcesService;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/25.
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesServiceImpl;
    @RequestMapping
    public String getAll(){
        return "resources/resources.index";
    }
    @ResponseBody
    @RequestMapping("/pageList")
    public Map<String,Object> getAll(
                                     @RequestParam(required = false, defaultValue = "0") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<Resources> pageInfo = resourcesServiceImpl.selectByPage(start, length);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
    @ResponseBody
    @RequestMapping("/resourcesWithSelected")
    public List<Resources> resourcesWithSelected(Integer rid){
        return resourcesServiceImpl.queryResourcesListWithSelected(rid);
    }
    @ResponseBody
    @RequestMapping("/loadMenu")
    public List<Resources> loadMenu(){
        Map<String,Object> map = new HashMap<>();
        Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
        map.put("type",1);
        map.put("userid",userid);
        List<Resources> resourcesList = resourcesServiceImpl.loadUserResources(map);
        return resourcesList;
    }
    @ResponseBody
    //@CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/add")
    public ReturnT<String> add(Resources resources){
        if (!(resources.getId() == null)){
            return update(resources);
        }
        if (!SecurityUtils.getSubject().isPermitted("/resources/add")){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足!");
        }
        try{
            resourcesServiceImpl.addResources(resources);

            return ReturnT.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
    public ReturnT<String> update(Resources resources){
        if (!SecurityUtils.getSubject().isPermitted("/resources/update")){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足!");
        }
        try{
            resourcesServiceImpl.updateResources(resources);
            return ReturnT.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
    @ResponseBody
    //@CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/delete")
    public ReturnT<String> delete(Integer id){
        if (!SecurityUtils.getSubject().isPermitted("/resources/delete")){
            return new ReturnT<String>(ReturnT.FAIL_CODE,"权限不足!");
        }
        try{
            resourcesServiceImpl.deleteById(id);
            //更新权限
//            shiroService.updatePermission();
            return ReturnT.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
}
