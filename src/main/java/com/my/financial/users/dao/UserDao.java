package com.my.financial.users.dao;

import com.my.financial.ds.UserProvider;
import com.my.financial.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Mapper
public interface UserDao {
    @Select("select * from user")
    List<User> getUsers();
    @Select("select u.*,f.family_name from user u left join family f on f.id = u.family_id where u.id=#{id}")
    User getUserById(@Param("id") Integer id);
    @Select("select u.*,f.family_name from user u left join family f on f.id = u.family_id where phone=#{phone}")
    User getUserByPhone(@Param("phone") String phone);
    @Select("select * from user where username=#{username}")
    User getUserByUsername(@Param("username") String username);
    @Select("select u.*,f.family_name from user u left join family f on f.id = u.family_id where username=#{username} and password=#{password}")
    User getUserByIdAndPassword(@Param("username") String id,@Param("password") String password);
    @Insert("insert into user(phone) values(#{phone})")
    int createUserByPhone(@Param("phone") String phone);
    @Delete("delete from user where id=#{id}")
    int deleteUser(@Param("id") Integer id);
    @UpdateProvider(type = UserProvider.class, method="updateUser")
    int updateUser(User user);
    @Update("update user set phone = #{phone} where id = #{id}")
    int updatePhone(@Param("id") Integer id,@Param("phone") String phone);
    @Select("select * from user where family_id=#{id}")
    List<User> fingUserByFamilyId(@Param("id") Integer id);
    @Select("select u.* from user u left join family f on f.id = u.family_id  where f.id = #{familyId}")
    List<User> getUsersByFamilyId(@Param("familyId") Integer familyId);
    @Update("update user set password = #{user.password} where phone = #{user.phone}")
    int updateUserByPhone(@Param("user") User user);
}
