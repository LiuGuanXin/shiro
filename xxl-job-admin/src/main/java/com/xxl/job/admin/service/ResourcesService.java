package com.xxl.job.admin.service;

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
}
