package com.my.financial.income.service;

import com.my.financial.model.Condition;
import com.my.financial.model.Income;
import com.my.financial.model.TimeDto;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
public interface IncomeService  {
    //get
    CommResult getAllIncome(Integer userId, Condition condition);
    //post
    CommResult createIncome(Income income);
    //delete
    CommResult deleteIncome(Integer id);
    // put
    CommResult updateIncome(Integer id,Income income);
    //options Query
    CommResult getIncome(String options);
    //get by time
    CommResult getIncomeByTime(TimeDto timeDto);
}
