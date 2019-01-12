package com.xxl.job.admin.sqlProvider;

import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 09:45
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public class ResourcesProvider {

    public String loadUserResources(Map<String,Object> para){
        Map<String,Object> map = (Map<String, Object>) para.get("map");
        String sql = "SELECT re.id,re.name,re.parentId,re.resUrl " +
                "FROM resources re LEFT JOIN role_resources rr ON re.id = rr.resourcesId " +
                " LEFT JOIN user_role ur ON rr.roleId =ur.roleId WHERE ur.userId='"+ map.get("userid")+"'";
        if (map.get("type") != null){
            sql += " AND re.type= '" + map.get("type")+ "'";
        }
        sql += " GROUP BY re.id ORDER BY re.sort ASC";

        return sql;
    }
}
