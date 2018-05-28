package com.my.financial.query.service;

import com.my.financial.model.Condition;
import com.my.financial.result.CommResult;

public interface QuerySerivce {
    CommResult queryAll(Integer userId,Condition condition);

}
