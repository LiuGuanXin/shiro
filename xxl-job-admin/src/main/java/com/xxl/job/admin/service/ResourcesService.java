package com.xxl.job.admin.service;

import com.github.pagehelper.PageInfo;
import com.xxl.job.admin.model.Resources;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:36
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public interface ResourcesService {
    public List<Resources> loadUserResources(Map<String, Object> map);
    public PageInfo<Resources> selectByPage(Resources resources,int start, int length);
    public List<Resources> queryResourcesListWithSelected(Integer rid);
    public List<Resources> queryAll();
    public void addResources(Resources resources);
    public void deleteById(Integer id);
}
