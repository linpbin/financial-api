package com.my.financial.message.dao;

import com.my.financial.ds.MessageProvider;
import com.my.financial.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDao {
    @Select("select * from message where family_id=#{familyId} order by create_date desc")
    List<Message> findMessageList(@Param("familyId") Integer familyId);
    @Select("select * from message where id = #{aid}")
    Message findMessage(@Param("aid") Integer aid);
    @Insert("insert into message(family_id,content,title,create_date,create_by,update_date,update_by) values(#{message.familyId},#{message.content},#{message.title},#{message.createDate},#{message.createBy},#{message.updateDate},#{message.updateBy})")
    int createMessage(@Param("message") Message message);
    @UpdateProvider(type = MessageProvider.class,method = "updateMessage")
    int updateMessage(Message message);
    @Delete("delete from message where id=#{aid}")
    int deleteMessage(@Param("aid") Integer aid);
}
