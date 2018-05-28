package com.my.financial.graph.dao;

import com.my.financial.model.Graph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GraphDao {
    @Select("select it.type_name as name,sum(i.sum) as value from income i  left join income_type it on it.id = i.income_type  where i.user_id=#{userId} group by type_name")
    List<Graph> selectIncomeList(@Param("userId") Integer userId);
    @Select("select et.type_name as name,sum(e.sum) as value from expend e  left join expend_type et on et.id = e.expend_type  where e.user_id=#{userId} group by type_name")
    List<Graph> selectExpendList(@Param("userId") Integer userId);
    @Select("select et.type_name as name,sum(e.sum) as value from expend e  left join expend_type et on et.id = e.expend_type  where e.user_id=#{userId} and e.expend_date between #{startTime} and #{endTime} group by type_name")
    List<Graph> selectExpendListBoth(@Param("userId") Integer userId,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Select("select et.type_name as name,sum(e.sum) as value from expend e  left join expend_type et on et.id = e.expend_type  where e.user_id=#{userId} and e.expend_date > #{startTime}  group by type_name")
    List<Graph> selectExpendListStart(@Param("userId") Integer userId,@Param("startTime") String startTime);
    @Select("select et.type_name as name,sum(e.sum) as value from expend e  left join expend_type et on et.id = e.expend_type  where e.user_id=#{userId} and e.expend_date < #{endTime}  group by type_name")
    List<Graph> selectExpendListEnd(@Param("userId") Integer userId,@Param("endTime") String endTime);
    @Select("select it.type_name as name,sum(i.sum) as value from income i  left join income_type it on it.id = i.income_type  where i.user_id=#{userId} and i.income_date between #{startTime} and #{endTime} group by type_name")
    List<Graph> selectIncomeListBoth(@Param("userId") Integer userId,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Select("select it.type_name as name,sum(i.sum) as value from income i  left join income_type it on it.id = i.income_type  where i.user_id=#{userId} and i.income_date > #{startTime} group by type_name")
    List<Graph> selectIncomeListStart(@Param("userId") Integer userId,@Param("startTime") String startTime);
    @Select("select it.type_name as name,sum(i.sum) as value from income i  left join income_type it on it.id = i.income_type  where i.user_id=#{userId} and i.income_date < #{endTime} group by type_name")
    List<Graph> selectIncomeListEnd(@Param("userId") Integer userId,@Param("endTime") String endTime);
}
