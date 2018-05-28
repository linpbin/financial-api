package com.my.financial.expend.dao;

import com.my.financial.ds.ExpendProvider;
import com.my.financial.model.Expend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Mapper
public interface ExpendDao {
    @Select("select e.*,et.type_name from expend e left join expend_type et on et.id = e.expend_type  where e.user_id=#{userId} order by expend_date desc")
    List<Expend> selectExpendList(@Param("userId") Integer userId);
    @Insert("insert into expend(sum,expend_type,expend_date,remark,user_id,update_time) values(#{expend.sum},#{expend.expendType},#{expend.expendDate},#{expend.remark},#{expend.userId},#{expend.expendDate})")
    int createExpend(@Param("expend") Expend expend);
    @Delete("delete from expend where id=#{id}")
    int deleteExpend(@Param("id") Integer id);
    @UpdateProvider(type = ExpendProvider.class,method = "updateExpend")
    int updateExpend(Expend expend);
    @Select("select * from expend where expend_date=#{time} and user_id=#{userId}")
    List<Expend> selectExpendListByTime(@Param("time")Date time, @Param("userId") Integer userId);
    @Select("select * from expend where expend_date=#{time} between #{startTime} and #{endTime} and user_id=#{userId}")
    List<Expend> selectExpendListByDate(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("userId") Integer userId);

}
