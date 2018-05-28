package com.my.financial.graph.service.impl;

import com.my.financial.graph.dao.GraphDao;
import com.my.financial.graph.service.GraphService;
import com.my.financial.model.Condition;
import com.my.financial.model.Graph;
import com.my.financial.result.CommResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GraphgServiceImpl implements GraphService {
    @Autowired
    private GraphDao graphDao;

    @Override
    public CommResult getExpendTotal(Integer userId, Condition condition) {
        CommResult commResult = null;
        if (condition.getStartTime() == null && condition.getEndTime() == null) {
            List<Graph> expendList = graphDao.selectExpendList(userId);
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else if (condition.getStartTime()!=null && condition.getEndTime() == null){
            List<Graph> expendList = graphDao.selectExpendListStart(userId,condition.getStartTime());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else if (condition.getStartTime()==null && condition.getEndTime() != null){
            List<Graph> expendList = graphDao.selectExpendListEnd(userId,condition.getEndTime());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else {
            List<Graph> expendList = graphDao.selectExpendListBoth(userId,condition.getStartTime(),condition.getEndTime());
            System.out.println(expendList.toString());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }

    }

    @Override
    public CommResult getIncomeTotal(Integer userId, Condition condition) {
        CommResult commResult = null;
        if (condition.getStartTime() == null && condition.getEndTime() == null) {
            List<Graph> expendList = graphDao.selectIncomeList(userId);
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else if(condition.getStartTime()!=null && condition.getEndTime() == null){
            List<Graph> expendList = graphDao.selectIncomeListStart(userId,condition.getStartTime());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else if (condition.getStartTime()==null && condition.getEndTime() != null){
            List<Graph> expendList = graphDao.selectIncomeListEnd(userId,condition.getEndTime());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }else {
            List<Graph> expendList = graphDao.selectIncomeListBoth(userId,condition.getStartTime(),condition.getEndTime());
            System.out.println(expendList.toString());
            if (expendList != null && expendList.size() > 0) {
                commResult = new CommResult(expendList);
                return commResult;
            } else {
                commResult = new CommResult(1, "failure");
                return commResult;
            }
        }
    }
}
