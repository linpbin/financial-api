package com.my.financial.ds;

import com.my.financial.model.Family;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
public class FamilyProvider {
    public String updateFamily(Family family){
        return  new SQL(){{
            UPDATE("family");
            if (family.getFamilyName()!=null){
                SET("family_name=#{familyName}");
            }
            if (family.getFamilyAdmin()!=null){
                SET("family_admin=#{familyAdmin}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
