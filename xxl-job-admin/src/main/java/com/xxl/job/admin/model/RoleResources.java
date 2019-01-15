package com.xxl.job.admin.model;


import java.io.Serializable;

//@Table(name = "role_resources")
public class RoleResources implements Serializable {
    private static final long serialVersionUID = -8559867942708057891L;
//    @Id
//    @Column(name = "roleId")
    private Integer roleId;

//    @Id
//    @Column(name = "resourcesId")
    private String resourcesId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }
}