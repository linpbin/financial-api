package com.my.financial.ds;


import com.my.financial.model.Income;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
public class IncomeProvider {
    public String updateIncome(Income income){
        return new SQL(){{
            UPDATE("income");
            if (income.getSum()!=null&&income.getSum()>0){
                SET("sum=#{sum}");
            }
            if (income.getIncomeType()!=null){
                SET("income_type=#{incomeType}");
            }
            if (income.getIncomeDate()!=null){
                SET("income_date=#{incomeDate}");
            }
            if (StringUtils.isNotBlank(income.getRemark())){
                SET("remark=#{remark}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
