package com.xxl.job.admin.service.impl;

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
}
