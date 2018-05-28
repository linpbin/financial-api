package com.my.financial.query.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.financial.model.Condition;
import com.my.financial.model.Expend;
import com.my.financial.model.Income;
import com.my.financial.model.Query;
import com.my.financial.query.dao.QueryDao;
import com.my.financial.query.service.QuerySerivce;
import com.my.financial.result.CommResult;
import com.my.financial.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class QueryServiceImpl implements QuerySerivce {
    @Autowired
    private QueryDao queryDao;

    @Override
    public CommResult queryAll(Integer userId, Condition condition) {
        StringBuilder expend = new StringBuilder();
        StringBuilder income = new StringBuilder();
        expend.append("select e.id,e.sum,et.type_name as typeName,e.expend_date as time,e.remark,e.user_id,e.type from expend e LEFT JOIN expend_type et on e.expend_type  = et.id ");
        String union = " union all ";
        income.append("select i.id,i.sum,it.type_name as typeName,i.income_date as time,i.remark,i.user_id,i.type from income i LEFT JOIN income_type it on i.income_type  = it.id ");
        String sort = " order by time desc";
        StringBuilder result = new StringBuilder();
        if (condition.getPageSize() == null || condition.getPageSize() == 0) {
            condition.setPageSize(10);
        }
        if (condition.getPageNo() == null || condition.getPageNo() == 0) {
            condition.setPageNo(1);
        }
        if ("支出".equals(condition.getType())) {
            if (condition.getTypeName() == null) {
                if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                    expend.append("where e.sum > '" + condition.getMinSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                    expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                    expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                    expend.append("where e.sum < '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");

                } else {
                    if (condition.getStartTime() != null && condition.getEndTime() == null) {
                        expend.append("where e.expend_date > '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                        expend.append(" where e.expend_date < '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                        expend.append(" where e.expend_date = '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                        expend.append(" where e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    } else {
                        if (condition.getRemark() != null)
                            expend.append(" where e.remark like '%" + condition.getRemark() + "%'");
                    }
                }
            } else {
                if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                    expend.append("where e.sum > '" + condition.getMinSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    expend.append(" and et.type_name ='" + condition.getTypeName() + "'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                    expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    expend.append(" and et.type_name ='" + condition.getTypeName() + "'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                    expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    expend.append(" and et.type_name ='" + condition.getTypeName() + "'");
                } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                    expend.append("where e.sum < '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                    expend.append(" and et.type_name = '" + condition.getTypeName() + "'");

                } else {
                    if (condition.getStartTime() != null && condition.getEndTime() == null) {
                        expend.append("where e.expend_date > '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                        expend.append(" and et.type_name = '" + condition.getTypeName() + "'");
                    } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                        expend.append(" where e.expend_date < '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                        expend.append(" and et.type_name = '" + condition.getTypeName() + "'");
                    } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                        expend.append(" where e.expend_date = '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                        expend.append(" and et.type_name = '" + condition.getTypeName() + "'");
                    } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                        expend.append(" where e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                        expend.append(" and et.type_name = '" + condition.getTypeName() + "'");
                    } else {
                        if (condition.getRemark() != null)
                            expend.append(" where e.remark like '%" + condition.getRemark() + "%'");
                        else {
                            expend.append(" where et.type_name = '" + condition.getTypeName() + "'");
                        }
                    }
                }
            }
            result.append(expend);
        } else if ("收入".equals(condition.getType()) ) {
            System.out.println("shouru");
            if (condition.getTypeName() == null) {
                if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                    income.append("where i.sum > '" + condition.getMinSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                    income.append("where i.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                    income.append("where i.sum between '" + condition.getMinSum() + " and " + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                    income.append("where i.sum < '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");

                } else {
                    if (condition.getStartTime() != null && condition.getEndTime() == null) {
                        income.append("where i.income_date > '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                        income.append(" where i.income_date < '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                        income.append(" where i.income_date = '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                        income.append(" where i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    } else {
                        if (condition.getRemark() != null)
                            income.append(" where i.remark like '%" + condition.getRemark() + "%'");
                    }
                }
            } else {
                if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                    income.append("where i.sum > '" + condition.getMinSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    income.append(" and it.type_name = '" + condition.getTypeName()+"'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                    income.append("where i.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    income.append(" and it.type_name = '" + condition.getTypeName() + "'");
                } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                    income.append("where i.sum between '" + condition.getMinSum() + " and " + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    income.append(" and it.type_name = '" + condition.getTypeName() + "'");
                } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                    income.append("where i.sum < '" + condition.getMaxSum() + "'");
                    if (condition.getStartTime() != null && condition.getEndTime() == null)
                        income.append("and i.income_date > '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() == null && condition.getEndTime() != null)
                        income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                    else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                        income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                    else if (condition.getStartTime() != null && condition.getEndTime() != null)
                        income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and i.remark like '%" + condition.getRemark() + "%'");
                    income.append(" and it.type_name = '" + condition.getTypeName() + "'");

                } else {
                    if (condition.getStartTime() != null && condition.getEndTime() == null) {
                        income.append("where i.income_date > '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                        income.append(" and it.type_name = '" + condition.getTypeName()+"'");
                    } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                        income.append(" where i.income_date < '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                        income.append(" and it.type_name = '" + condition.getTypeName() + "'");
                    } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                        income.append(" where i.income_date = '" + condition.getStartTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                        income.append(" and it.type_name = '" + condition.getTypeName() + "'");
                    } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                        income.append(" where i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                        if (condition.getRemark() != null)
                            income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                        income.append(" and it.type_name = '" + condition.getTypeName() + "'");
                    } else {
                        if (condition.getRemark() != null)
                            income.append(" where i.remark like '%" + condition.getRemark() + "%'");
                        else {
                            income.append(" where it.type_name = '" + condition.getTypeName() + "'");
                        }
                    }
                }
            }
            result.append(income);
        } else {
            if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                expend.append("where e.sum > '" + condition.getMinSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                expend.append("where e.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                expend.append("where e.sum < '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    expend.append("and e.expend_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    expend.append(" and e.expend_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    expend.append(" and e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    expend.append(" and e.remark like '%" + condition.getRemark() + "%'");

            } else {
                if (condition.getStartTime() != null && condition.getEndTime() == null) {
                    expend.append("where e.expend_date > '" + condition.getStartTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark()+"%'");
                } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                    expend.append(" where e.expend_date < '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                    expend.append(" where e.expend_date = '" + condition.getStartTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                    expend.append(" where e.expend_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        expend.append(" and e.remark like '%" + condition.getRemark() + "%'");
                } else {
                    if (condition.getRemark() != null)
                        expend.append(" where e.remark like '%" + condition.getRemark() + "%'");
                }
            }
            if (condition.getMinSum() != null && condition.getMaxSum() == null) {
                income.append("where i.sum > '" + condition.getMinSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    income.append("and i.income_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    income.append(" and i.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() != null && condition.getMaxSum() != null && condition.getMinSum() != condition.getMaxSum()) {
                income.append("where i.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    income.append("and i.income_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    income.append(" and i.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() != null && condition.getMaxSum() != null) {
                income.append("where i.sum between '" + condition.getMinSum() + "' and '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    income.append("and i.income_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    income.append(" and i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    income.append(" and i.remark like '%" + condition.getRemark() + "%'");
            } else if (condition.getMinSum() == null && condition.getMaxSum() != null) {
                income.append("where i.sum < '" + condition.getMaxSum() + "'");
                if (condition.getStartTime() != null && condition.getEndTime() == null)
                    income.append("and i.income_date > '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() == null && condition.getEndTime() != null)
                    income.append(" and i.income_date < '" + condition.getEndTime() + "'");
                else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime()))
                    income.append(" and i.income_date = '" + condition.getStartTime() + "'");
                else if (condition.getStartTime() != null && condition.getEndTime() != null)
                    income.append(" and i.income_date between'" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                if (condition.getRemark() != null)
                    income.append(" and i.remark like '%" + condition.getRemark() + "%'");

            } else {
                if (condition.getStartTime() != null && condition.getEndTime() == null) {
                    income.append("where i.income_date > '" + condition.getStartTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getStartTime() == null && condition.getEndTime() != null) {
                    income.append(" where i.income_date < '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getStartTime() != null && condition.getStartTime().equals(condition.getEndTime())) {
                    income.append(" where i.income_date = '" + condition.getStartTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else if (condition.getStartTime() != null && condition.getEndTime() != null) {
                    income.append(" where i.income_date between '" + condition.getStartTime() + "' and '" + condition.getEndTime() + "'");
                    if (condition.getRemark() != null)
                        income.append(" and i.remark like '%" + condition.getRemark() + "%'");
                } else {
                    if (condition.getRemark() != null)
                        income.append(" where i.remark like '%" + condition.getRemark() + "%'");
                }
            }
            result.append(expend);
            result.append(union);
            result.append(income);
            result.append(sort);

        }

        System.out.println(result.toString());
        CommResult<PageInfo<Query>> commResult = null;
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        List<Query> result1 = queryDao.findAll(result.toString());
        PageInfo<Query> page = new PageInfo<>(result1);
        if (page.getList() != null && page.getList().

                size() > 0)

        {
            commResult = new CommResult(page);
        } else

        {
            commResult = new CommResult(1, "没有记录");
        }
        return commResult;
    }
}
