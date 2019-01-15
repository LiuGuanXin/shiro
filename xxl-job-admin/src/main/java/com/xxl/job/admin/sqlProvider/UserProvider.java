package com.xxl.job.admin.sqlProvider;

import com.github.pagehelper.util.StringUtil;
import com.xxl.job.admin.model.User;
import org.apache.ibatis.jdbc.SQL;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-15 16:23
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public class UserProvider {

    public String selectByPage(final Map<String,Object> para){
        return new SQL(){{
            User user = (User) para.get("user");
            SELECT("*");
            FROM("user");
            if (StringUtil.isNotEmpty(user.getUsername())) {
                WHERE("username LIKE '%"+ user.getUsername() + "%'");
            }
            if (user.getId() != null) {
                WHERE("id = '"+ user.getId() + "'");
            }
            if (user.getEnable() != null) {
                WHERE("enable = '"+ user.getEnable() + "'");
            }
        }}.toString();
    }
}
