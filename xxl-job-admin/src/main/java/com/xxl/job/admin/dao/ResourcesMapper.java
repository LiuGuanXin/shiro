package com.xxl.job.admin.dao;

import com.xxl.job.admin.model.Resources;
import com.xxl.job.admin.sqlProvider.ResourcesProvider;
import org.apache.ibatis.annotations.*;

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
    @Select("SELECT re.id,re.name,re.parentId,re.resUrl,re.type, (CASE WHEN EXISTS(SELECT 1 FROM role_resources rr \n" +
            "WHERE rr.resourcesId=re.id AND rr.roleId=#{rid})THEN 'true' ELSE 'false' END) AS checked\n" +
            "FROM resources re WHERE re.parentId !=0")
    public List<Resources> queryResourcesListWithSelected(Integer rid);


    @Insert("INSERT INTO resources (name,resUrl,type,parentId) values(#{name},#{resurl},#{type},#{parentid})")
    public void insert(Resources resources);
    @Delete("DELETE from resources WHERE id = #{id}")
    public void delete(Integer id);
    @Delete("update resources set name=#{name},resUrl=#{resurl},type=#{type},parentId=#{parentid} WHERE id = #{id}")
    public void update(Resources resources);
}
