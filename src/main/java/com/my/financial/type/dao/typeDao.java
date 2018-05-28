package com.my.financial.type.dao;

import com.my.financial.model.ExpendType;
import com.my.financial.model.IncomeType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface typeDao {
    @Select("select * from income_type where user_id = #{userId} or dr = 0")
    List<IncomeType> findAllIncomeTypeByUserId(@Param("userId") Integer userId);
    @Select("select * from expend_type where user_id = #{userId} or dr = 0")
    List<ExpendType> findAllExpendTypeByUserId(@Param("userId") Integer userId);
    @Select("select * from income_type where user_id = #{userId} and dr = 1")
    List<IncomeType> findAllIncomeTypeForUser(@Param("userId") Integer userId);
    @Select("select * from expend_type where user_id = #{userId} and dr = 1")
    List<ExpendType> findAllExpendTypeForUser(@Param("userId") Integer userId);
    @Insert("insert into income_type (type_name,user_id,dr) values(#{incomeType.typeName},#{incomeType.userId},1)")
    int createIncomeType(@Param("incomeType") IncomeType incomeType);
    @Update("update income_type set type_name = #{incomeType.typeName} where id = #{incomeType.id}")
    int updateIncomeTypeById(@Param("incomeType") IncomeType incomeType);
    @Update("update expend_type set type_name = #{expendType.typeName} where id = #{expendType.id}")
    int updateExpendTypeById(@Param("expendType") ExpendType expendType);
    @Delete("delete from income_type where id = #{id}")
    int deleteIncomeTypeById(@Param("id") Integer id);
    @Delete("delete from expend_type where id = #{id}")
    int deleteExpendTypeById(@Param("id") Integer id);
    @Insert("insert into expend_type (type_name,user_id,dr) values(#{expendType.typeName},#{expendType.userId},1)")
    int createExpendType(@Param("expendType") ExpendType expendType);

    @Delete("delete from income where id = #{id}")
    int deleteIncomeById(@Param("id") Integer id);
    @Delete("delete from expend where id = #{id}")
    int deleteExpendById(@Param("id") Integer id);
}
