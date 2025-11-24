package com.goldnexusbackend.mapper;

import com.goldnexusbackend.entity.User;
import com.goldnexusbackend.entity.UserDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {

    //user_basic
    @Select("select * from user_basic where username = #{username}")
    User selectUserByUsername(String username);

    @Insert("insert into user_basic(username,password) value (#{username},#{password})")
    void insertUser(User user);


    //user_details
    @Select("select * from user_details where username = #{username}")
    UserDetail selectUserDetailByUsername(String username);

    @Insert("insert into user_details value(#{username},#{name},#{gender},#{age},#{real_name_authentication},#{phone},#{address},#{marriage},#{job},#{education},#{earnings})")
    int insertUserDetail(UserDetail userDetail);

    @Update("update user_details set name=#{name},gender = #{gender},age=#{age},real_name_authentication=#{real_name_authentication},phone=#{phone},address=#{address},marriage=#{marriage},job=#{job},education=#{education},earnings=#{earnings} where username = #{username}")
    int updateUserDetail(UserDetail userDetail);
}
