package com.my.financial.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Query {
    private Integer id;
    private Double sum;
    private String typeName;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String remark;
    private Integer userId;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

//    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
