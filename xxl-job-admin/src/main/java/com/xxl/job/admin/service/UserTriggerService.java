package com.xxl.job.admin.service;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-17 17:09
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public interface UserTriggerService {
    public Integer queryUserIdByTriggerId(Integer id);
    public int saveUserInfo(int userId,int triggerId);
    public int delete(int triggerId);
    public int deleteByUser(int userId);
}
