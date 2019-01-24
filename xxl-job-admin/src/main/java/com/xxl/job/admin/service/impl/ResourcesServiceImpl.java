package com.xxl.job.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.dao.ResourcesMapper;
import com.xxl.job.admin.model.Resources;
import com.xxl.job.admin.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:39
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    public ResourcesMapper resourcesMapper;
    @Override
    public List<Resources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }

    @Override
    public PageInfo<Resources> selectByPage(int start, int length) {
        int page = start/length + 1;
        //分页查询
        PageHelper.startPage(page, length);
        List<Resources> resourcesList = resourcesMapper.queryAll();
        return new PageInfo<>(resourcesList);
    }

    @Override
    public List<Resources> queryResourcesListWithSelected(Integer rid) {
        return resourcesMapper.queryResourcesListWithSelected(rid);
    }

    @Override
    public List<Resources> queryAll() {
        return resourcesMapper.queryAll();
    }

    @Override
    public void addResources(Resources resources) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
