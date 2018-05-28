package com.my.financial.ds;

import com.my.financial.model.Message;
import org.apache.ibatis.jdbc.SQL;

public class MessageProvider {
    public String updateMessage(Message message){
        return new SQL(){{
            UPDATE("message");
            if (message.getContent()!=null){
                SET("content=#{content}");
            }
            if (message.getTitle()!=null){
                SET("title=#{title}");
            }
            if (message.getUpdateDate()!=null){
                SET("update_date=#{updateDate}");
            }
            if (message.getUpdateBy()!=null){
                SET("update_by=#{updateBy}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
