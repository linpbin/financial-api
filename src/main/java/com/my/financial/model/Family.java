package com.my.financial.model;

import java.util.Date;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
public class Family {
    private Integer id;
    private String familyName;
    private Integer membersNum;
    private Integer familyAdmin;
    private Date createDate;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Integer getMembersNum() {
        return membersNum;
    }

    public void setMembersNum(Integer membersNum) {
        this.membersNum = membersNum;
    }

    public Integer getFamilyAdmin() {
        return familyAdmin;
    }

    public void setFamilyAdmin(Integer familyAdmin) {
        this.familyAdmin = familyAdmin;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
