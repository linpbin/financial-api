package com.my.financial.announces.dao;

import com.my.financial.ds.AnnouncesProvider;
import com.my.financial.model.Announces;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
@Mapper
public interface AnnouncesDao {
    @Select("select * from announces where family_id=#{familyId} order by create_date desc")
    List<Announces> findAnnouncesList(@Param("familyId") Integer familyId);
    @Select("select * from announces where id = #{aid}")
    Announces findAnnouces(@Param("aid") Integer aid);
    @Insert("insert into announces(family_id,content,title,create_date,create_by,update_date,update_by) values(#{announces.familyId},#{announces.content},#{announces.title},#{announces.createDate},#{announces.createBy},#{announces.updateDate},#{announces.updateBy})")
    int createAnnounces(@Param("announces") Announces announces);
    @UpdateProvider(type = AnnouncesProvider.class,method = "updateAnnounces")
    int updateAnnounces(Announces announces);
    @Delete("delete from announces where id=#{aid}")
    int deleteAnnounces(@Param("aid") Integer aid);
}
