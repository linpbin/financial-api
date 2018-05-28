package com.my.financial.expend.service;

import com.my.financial.model.Condition;
import com.my.financial.model.Expend;
import com.my.financial.model.TimeDto;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
public interface ExpendService {
    //get
    CommResult getAllExpend(Integer userId, Condition condition);
    //post
    CommResult createExpend(Expend expend);
    //delete
    CommResult deleteExpend(Integer id);
    // put
    CommResult updateExpend(Integer id,Expend expend);
    //options Query
    CommResult getExpend(String options);
    CommResult getExpendByTime(TimeDto timeDto);
}
