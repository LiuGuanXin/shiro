package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.dao.UserTriggerMapper;
import com.xxl.job.admin.service.UserTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-17 17:09
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
@Service
public class UserTriggerServiceImpl implements UserTriggerService {
    @Autowired
    UserTriggerMapper userTriggerMapper;
    @Override
    public Integer queryUserIdByTriggerId(Integer id) {
        return userTriggerMapper.queryUserIdByTriggerId(id);
    }

    @Override
    public int saveUserInfo(int userId, int triggerId) {
        return userTriggerMapper.saveUserInfo(userId,triggerId);
    }

    @Override
    public int delete(int triggerId) {
        return userTriggerMapper.delete(triggerId);
    }
    public int deleteByUser(int userId){
        return userTriggerMapper.deleteByUser(userId);
    }
}
