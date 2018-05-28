package com.my.financial.query.dao;

import com.my.financial.model.Expend;
import com.my.financial.model.Income;
import org.apache.ibatis.annotations.Mapper;

import com.my.financial.model.Query;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryDao {
    @Select("select e.id,e.sum,et.type_name,expend_date as time,e.remark from expend e left join expend_type et on e.expend_type = et.id where e.user_id=#{userId} order by expend_date desc")
    List<Query> queryExpend(@Param("userId") Integer userId);
    @Select("select e.id,e.sum,et.type_name,income_date as time,e.remark from income e left join income_type et on e.income_type = et.id where e.user_id=#{userId} order by income_date desc")
    List<Query> queryIncome(@Param("userId") Integer userId);
    @Select("select e.*,et.type_name as typeName from expend e left join expend_type et on et.id = e.expend_type where e.user_id = #{userId} order by expend_date desc ")
    List<Expend> findExpendList(@Param("userId") Integer userId);
    @Select("select i.*,it.type_name as typeName from income i left join income_type it on it.id = i.income_type where i.user_id = #{userId} order by income_date desc ")
    List<Income> findIncomeList(@Param("userId") Integer userId);
//    @Select("select e.id,e.sum,et.type_name as typeName,e.expend_date as time,e.remark,e.user_id,e.type from expend e LEFT JOIN expend_type et on e.expend_type  = et.id " +
//            "UNION ALL " +
//            " select i.id,i.sum,it.type_name as typeName,i.income_date as time,i.remark,i.user_id,i.type from income i LEFT JOIN income_type it on i.income_type  = it.id order by time desc")
    @Select("${result}")
    List<Query> findAll(@Param("result") String result);
}
