package com.my.financial.type.service.impl;

import com.my.financial.model.ExpendType;
import com.my.financial.type.dao.typeDao;
import com.my.financial.type.service.typeService;
import com.my.financial.model.IncomeType;
import com.my.financial.result.CommResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements typeService {
    private static final Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);
    @Autowired
    private typeDao typeDao;
    @Override
    public CommResult findIncomeTypeListByUserId(Integer userId) {
        List<IncomeType> incomeTypeList =  typeDao.findAllIncomeTypeByUserId(userId);
        CommResult commResult = null;
        if (incomeTypeList.size()>0&&incomeTypeList!=null){
            commResult = new CommResult(incomeTypeList);
        }else {
            commResult = new CommResult(null);
        }
        return commResult;
    }

    @Override
    public CommResult findExpendTypeListByUserId(Integer userId) {
        List<ExpendType> expendTypeList =  typeDao.findAllExpendTypeByUserId(userId);
        CommResult commResult = null;
        if (expendTypeList.size()>0&&expendTypeList!=null){
            commResult = new CommResult(expendTypeList);
        }else {
            commResult = new CommResult(null);
        }
        return commResult;
    }

    @Override
    public CommResult findIncomeTypeListForUser(Integer userId) {
        List<IncomeType> incomeTypeList =  typeDao.findAllIncomeTypeForUser(userId);
        CommResult commResult = null;
        if (incomeTypeList.size()>0&&incomeTypeList!=null){
            commResult = new CommResult(incomeTypeList);
        }else {
            commResult = new CommResult(1,"查询失败");
        }
        return commResult;
    }

    @Override
    public CommResult findExpendTypeListForUser(Integer userId) {
        List<ExpendType> expendTypeList =  typeDao.findAllExpendTypeForUser(userId);
        CommResult commResult = null;
        if (expendTypeList.size()>0&&expendTypeList!=null){
            commResult = new CommResult(expendTypeList);
        }else {
            commResult = new CommResult(1,"查询失败");
        }
        return commResult;
    }

    @Override
    public CommResult createIncomeType(IncomeType incomeType) {
        CommResult commResult = null;
        int result = typeDao.createIncomeType(incomeType);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"新增失败！");
        }
        return commResult;
    }

    @Override
    public CommResult createExpendType(ExpendType expendType) {
        CommResult commResult = null;
        int result = typeDao.createExpendType(expendType);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"新增失败！");
        }
        return commResult;
    }

    @Override
    public CommResult updateIncomeType(IncomeType incomeType) {
        CommResult commResult = null;
        int result = typeDao.updateIncomeTypeById(incomeType);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"更新失败！");
        }
        return commResult;
    }

    @Override
    public CommResult updateExpendType(ExpendType expendType) {
        CommResult commResult = null;
        int result = typeDao.updateExpendTypeById(expendType);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"更新失败！");
        }
        return commResult;
    }

    @Override
    public CommResult deleteExpendTypeById(Integer id) {
        CommResult commResult = null;
        int result = typeDao.deleteExpendTypeById(id);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"删除失败！");
        }
        return commResult;
    }

    @Override
    public CommResult deleteIncomeTypeById(Integer id) {
        CommResult commResult = null;
        int result = typeDao.deleteIncomeTypeById(id);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"删除失败！");
        }
        return commResult;
    }

    @Override
    public CommResult deleteExpendById(Integer id) {
        CommResult commResult = null;
        int result = typeDao.deleteExpendById(id);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"删除失败！");
        }
        return commResult;
    }

    @Override
    public CommResult deleteIncomeById(Integer id) {
        CommResult commResult = null;
        int result = typeDao.deleteIncomeById(id);
        if (result!=0){
            commResult = new CommResult(null);
        }else {
            commResult = new CommResult(1,"删除失败！");
        }
        return commResult;
    }
}
