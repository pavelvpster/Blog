package org.interactiverobotics.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.interactiverobotics.blog.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users")
    @ResultMap("userMapper")
    List<User> findAll();

    @Select("select * from users where id = #{userId}")
    @Results(id = "userMapper",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "name", column = "name")
            })
    User findById(@Param("userId") long id);

    @Select("select * from users where id = #{userId}")
    @Results(id = "userMapperWithPosts",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "posts", javaType = List.class, column = "id",
                            many = @Many(select = "org.interactiverobotics.blog.mapper.PostMapper.findByUser",
                                    fetchType = FetchType.LAZY))
            })
    User findByIdWithPosts(@Param("userId") long id);

    @Select("select * from users where name = #{userName}")
    @ResultMap("userMapper")
    User findByName(@Param("userName") String name);
}
