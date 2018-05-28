package com.my.financial.ds;

import com.my.financial.model.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
public class UserProvider {
    public String updateUser(User user){
        return  new SQL(){{
            UPDATE("user");
            if (user.getPassword()!=null&&user.getPassword().length()>0){
                SET("password=#{password}");
            }
            if (user.getAge()!=null&&user.getAge()>0){
                SET("age=#{age}");
            }
            if (user.getSex()!=null&&user.getSex().length()>0){
                SET("sex=#{sex}");
            }
            if (user.getName()!=null&&user.getName().length()>0){
                SET("name=#{name}");
            }
            if (user.getUsername()!=null&&user.getName().length()>0){
                SET("username=#{username}");
            }
            if (user.getPhone()!=null&&user.getPhone().length()==11){
                SET("phone=#{phone}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
