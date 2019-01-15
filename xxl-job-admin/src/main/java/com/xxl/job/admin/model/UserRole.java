package com.xxl.job.admin.model;


import java.io.Serializable;

//@Table(name = "user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = -916411139749530670L;
//    @Column(name = "userId")
    private Integer userId;

//    @Column(name = "roleId")
    private String roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}