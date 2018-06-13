package com.atomikos.demo.atomikosdemo.dao.income;

import com.atomikos.demo.atomikosdemo.domain.Income;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IncomeMapper {
    @Insert("insert into income(userId,amount,operateDate) values(#{income.userId},#{income.amount},#{income.operateDate})")
    int insertIncome(@Param("income") Income income);
}
