package org.interactiverobotics.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.interactiverobotics.blog.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users")
    List<User> findAll();

    @Select("select * from users where id = #{userId}")
    User findById(@Param("userId") long id);

    @Select("select * from users where name = #{userName}")
    User findByName(@Param("userName") String name);
}
