package com.my.financial.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Condition {
    private Integer pageNo;
    private Integer pageSize;
    private Double minSum;
    private Double maxSum;
    private String startTime;
    private String endTime;
    private String remark;
    private String type;
    private String typeName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getMinSum() {
        return minSum;
    }

    public void setMinSum(Double minSum) {
        this.minSum = minSum;
    }

    public Double getMaxSum() {
        return maxSum;
    }

    public void setMaxSum(Double maxSum) {
        this.maxSum = maxSum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @Override
    public String toString() {
        return "Condition{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", minSum=" + minSum +
                ", maxSum=" + maxSum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                '}';
    }

}
