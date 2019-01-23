package com.xxl.job.admin.dao;

import org.apache.ibatis.annotations.*;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-17 17:06
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Mapper
public interface UserTriggerMapper {
    @Select("select userId from user_trigger_info where triggerId = #{id} and isDel = '1'")
    public Integer queryUserIdByTriggerId(Integer id);
    @Insert("INSERT INTO user_trigger_info(userId,triggerId) value(#{userId},#{triggerId})")
    public int saveUserInfo(@Param("userId")int userId, @Param("triggerId")int triggerId);

    @Update("UPDATE user_trigger_info set isDel = '0' WHERE triggerId = #{triggerId}")
    public int delete(@Param("triggerId")int triggerId);

    @Update("UPDATE user_trigger_info set isDel = '0' WHERE userId = #{userId}")
    public int deleteByUser(@Param("userId")int userId);
}
