package com.my.financial.income.dao;


import com.my.financial.ds.IncomeProvider;
import com.my.financial.model.Income;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Mapper
public interface IncomeDao {
    @Select("select i.*,it.type_name from income i  left join income_type it on it.id = i.income_type where i.user_id=#{userId} order by income_date desc")
    List<Income> selectIncomeList(@Param("userId") Integer userId);
    @Insert("insert into income(sum,income_type,income_date,update_time,remark,user_id) values(#{income.sum},#{income.incomeType},#{income.incomeDate},#{income.incomeDate},#{income.remark},#{income.userId})")
    int createIncome(@Param("income") Income income);
    @Delete("delete from income where id=#{id}")
    int deleteIncome(@Param("id") Integer id);
    @UpdateProvider(type = IncomeProvider.class,method = "updateIncome")
    int updateIncome(Income income);
    @Select("select * from income where income_date=#{time} and user_id=#{userId}")
    List<Income> selectIncomeListByTime(@Param("time")Date time,@Param("userId") Integer userId);
    @Select("select * from income where income_date=#{time} between #{startTime} and #{endTime} and user_id=#{userId}")
    List<Income> selectIncomeListByDate(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("userId") Integer userId);

}
