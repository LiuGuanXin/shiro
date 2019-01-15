package com.xxl.job.admin.model;

//import javax.persistence.*;
import java.io.Serializable;

public class Role implements Serializable{
    private static final long serialVersionUID = -6140090613812307452L;

    private Integer id;

//    @Column(name = "roleDesc")
    private String roleDesc;
//    @Transient
    private Integer selected;
    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}