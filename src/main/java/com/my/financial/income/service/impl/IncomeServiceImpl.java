package com.my.financial.income.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.financial.income.dao.IncomeDao;
import com.my.financial.income.service.IncomeService;
import com.my.financial.model.Condition;
import com.my.financial.model.Income;
import com.my.financial.model.TimeDto;
import com.my.financial.result.CommResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeDao incomeDao;
    @Override
    public CommResult getAllIncome(Integer userId, Condition condition) {
        if (condition.getPageSize() == null || condition.getPageSize() == 0) {
            condition.setPageSize(10);
        }
        if (condition.getPageNo() == null || condition.getPageNo() == 0) {
            condition.setPageNo(1);
        }
        CommResult<PageInfo<Income>> commResult = null;
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        List<Income> incomeList = incomeDao.selectIncomeList(userId);
        PageInfo<Income> page = new PageInfo<>(incomeList);
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

    @Override
    public CommResult createIncome(Income income) {
        CommResult commResult=null;
        int result = incomeDao.createIncome(income);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(1,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult deleteIncome(Integer id) {
        CommResult commResult=null;
        int result = incomeDao.deleteIncome(id);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(0,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult updateIncome(Integer id,Income income) {
        CommResult commResult=null;
        income.setId(id);
        int result = incomeDao.updateIncome(income);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(0,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult getIncome(String options) {
        return null;
    }

    @Override
    public CommResult getIncomeByTime(TimeDto timeDto) {
        if (timeDto.getStartTime().compareTo(timeDto.getEndTime())>0){
            return new CommResult(1,"开始时间大于结束时间");
        }else if (timeDto.getStartTime().compareTo(timeDto.getEndTime())==0){
            List<Income> incomeList = incomeDao.selectIncomeListByTime(timeDto.getStartTime(),timeDto.getUserId());
            if (incomeList.size()>0&&incomeList!=null){
                return new CommResult(incomeList);
            }else {
                return new CommResult(1,"暂无记录");
            }
        }else {
            List<Income> incomeList = incomeDao.selectIncomeListByDate(timeDto.getStartTime(),timeDto.getEndTime(),timeDto.getUserId());
            if (incomeList.size()>0&&incomeList!=null){
                return new CommResult(incomeList);
            }else {
                return new CommResult(1,"暂无记录");
            }
        }
    }
}
