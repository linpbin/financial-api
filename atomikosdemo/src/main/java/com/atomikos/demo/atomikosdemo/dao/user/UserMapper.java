package com.atomikos.demo.atomikosdemo.dao.user;

import com.atomikos.demo.atomikosdemo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name) values(#{user.name})")
    int insertUser(@Param("user") User user);
}
