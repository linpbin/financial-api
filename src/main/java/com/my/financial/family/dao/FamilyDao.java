package com.my.financial.family.dao;

import com.my.financial.ds.FamilyProvider;
import com.my.financial.model.Family;
import com.my.financial.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
@Mapper
public interface FamilyDao {
    @Select("select * from family")
    List<Family> getFamily();
    @Select("select * from family where id = #{id}")
    Family getFamilyById(@Param("id") Integer id);
    @Select("select f.*,u.name from family f left join user u on u.family_id = f.id where u.id = #{userId}")
    Family getFamilyByUserId(@Param("userId") Integer userId);
    @Insert("insert into family(family_name,members_num,create_date,family_admin) values(#{family.familyName},#{family.membersNum},#{family.createDate},#{family.familyAdmin})")
    @Options(useGeneratedKeys = true,keyProperty = "family.id")
    int createFamily(@Param("family") Family family);
    @UpdateProvider(type = FamilyProvider.class,method = "updateFamily")
    int updateFamily(Family family);
    @Delete("delete from family where id=#{id}")
    int deleteFamily(@Param("id") Integer id);
    @Update("update user set user.family_id = #{familyId} where user.id = #{userId}")
    int addUser(@Param("familyId") Integer familyId,@Param("userId") Integer userId);
    @Update("update user set user.family_id = null where user.id = #{userId}")
    int delUser(@Param("familyId") Integer familyId,@Param("userId") Integer userId);
    @Update("update family set members_num = #{number} where id = #{id}")
    int updateNum(@Param("number") Integer number,@Param("id") Integer id);

}
