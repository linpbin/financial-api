package com.my.financial.type.service;

import com.my.financial.model.Expend;
import com.my.financial.model.ExpendType;
import com.my.financial.model.Income;
import com.my.financial.model.IncomeType;
import com.my.financial.result.CommResult;

public interface typeService {
    CommResult findIncomeTypeListByUserId(Integer userId);

    CommResult findExpendTypeListByUserId(Integer userId);
    CommResult findIncomeTypeListForUser(Integer userId);

    CommResult findExpendTypeListForUser(Integer userId);

    CommResult createIncomeType(IncomeType incomeType);

    CommResult createExpendType(ExpendType expendType);
    CommResult updateIncomeType(IncomeType incomeType);
    CommResult updateExpendType(ExpendType expendType);


    CommResult deleteExpendTypeById(Integer id);

    CommResult deleteIncomeTypeById(Integer id);

    CommResult deleteExpendById(Integer id);

    CommResult deleteIncomeById(Integer id);


}
