package com.xxl.job.admin.dao;

import com.xxl.job.admin.model.Resources;
import com.xxl.job.admin.sqlProvider.ResourcesProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:40
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface ResourcesMapper {
    @SelectProvider(type = ResourcesProvider.class,method = "loadUserResources")
    public List<Resources> loadUserResources(@Param("map") Map<String, Object> map);

    @Select("select * from resources")
    public List<Resources> queryAll();
}
