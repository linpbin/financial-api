package com.my.financial.expend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.financial.expend.dao.ExpendDao;
import com.my.financial.expend.service.ExpendService;
import com.my.financial.model.Condition;
import com.my.financial.model.Expend;
import com.my.financial.model.TimeDto;
import com.my.financial.result.CommResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Service
public class ExpendServiceImpl implements ExpendService {
    @Autowired
    private ExpendDao expendDao;
    @Override
    public CommResult getAllExpend(Integer userId,Condition condition) {
        if (condition.getPageSize() == null || condition.getPageSize() == 0) {
            condition.setPageSize(10);
        }
        if (condition.getPageNo() == null || condition.getPageNo() == 0) {
            condition.setPageNo(1);
        }
        CommResult<PageInfo<Expend>> commResult = null;
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        List<Expend> incomeList = expendDao.selectExpendList(userId);
        PageInfo<Expend> page = new PageInfo<>(incomeList);
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
    public CommResult createExpend(Expend expend) {
        CommResult commResult=null;
        int result = expendDao.createExpend(expend);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(1,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult deleteExpend(Integer id) {
        CommResult commResult=null;
        int result = expendDao.deleteExpend(id);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(0,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult updateExpend(Integer id,Expend expend) {
        CommResult commResult=null;
        expend.setId(id);
        int result = expendDao.updateExpend(expend);
        if (result!=0){
            commResult = new CommResult(null);
            return commResult;
        }else {
            commResult= new CommResult(0,"failure");
            return commResult;
        }
    }

    @Override
    public CommResult getExpend(String options) {
        return null;
    }

    @Override
    public CommResult getExpendByTime(TimeDto timeDto) {
        if (timeDto.getStartTime().compareTo(timeDto.getEndTime())>0){
            return new CommResult(1,"开始时间大于结束时间");
        }else if (timeDto.getStartTime().compareTo(timeDto.getEndTime())==0){
            List<Expend> incomeList = expendDao.selectExpendListByTime(timeDto.getStartTime(),timeDto.getUserId());
            if (incomeList.size()>0&&incomeList!=null){
                return new CommResult(incomeList);
            }else {
                return new CommResult(1,"暂无记录");
            }
        }else {
            List<Expend> incomeList = expendDao.selectExpendListByDate(timeDto.getStartTime(),timeDto.getEndTime(),timeDto.getUserId());
            if (incomeList.size()>0&&incomeList!=null){
                return new CommResult(incomeList);
            }else {
                return new CommResult(1,"暂无记录");
            }
        }
    }
}
