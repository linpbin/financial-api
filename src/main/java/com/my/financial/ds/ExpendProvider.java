package com.my.financial.ds;

import com.my.financial.model.Expend;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;



/**
 * Created by lin.pingbin on 2018/2/25.
 */
public class ExpendProvider {
    public String updateExpend(Expend expend){
        return new SQL(){{
            UPDATE("expend");
            if (expend.getSum()!=null&&expend.getSum()>0){
                SET("sum=#{sum}");
            }
            if (expend.getExpendType()!=null){
                SET("expend_type=#{expendType}");
            }
            if (expend.getExpendDate()!=null){
                SET("expend_date=#{expendDate}");
            }
            if (StringUtils.isNotBlank(expend.getRemark())){
                SET("remark=#{remark}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
