package com.my.financial.graph.service;

import com.my.financial.model.Condition;
import com.my.financial.result.CommResult;

public interface GraphService {
    CommResult getExpendTotal(Integer userId, Condition condition);
    CommResult getIncomeTotal(Integer userId,Condition condition);
}
