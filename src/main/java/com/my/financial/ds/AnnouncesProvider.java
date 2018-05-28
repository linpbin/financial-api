package com.my.financial.ds;

import com.my.financial.model.Announces;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class AnnouncesProvider {
    public String updateAnnounces(Announces announces){
        return new SQL(){{
            UPDATE("announces");
            if (announces.getContent()!=null){
                SET("content=#{content}");
            }
            if (announces.getTitle()!=null){
                SET("title=#{title}");
            }
            if (announces.getUpdateDate()!=null){
                SET("update_date=#{updateDate}");
            }
            if (announces.getUpdateBy()!=null){
                SET("update_by=#{updateBy}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
