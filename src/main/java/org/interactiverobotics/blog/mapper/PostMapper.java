package org.interactiverobotics.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.interactiverobotics.blog.model.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("select * from posts")
    @ResultMap("postMapper")
    List<Post> finaAll();

    @Select("select * from posts where id = #{postId}")
    @Results(id = "postMapper",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "user", column = "user_id",
                            one = @One(select = "org.interactiverobotics.blog.mapper.UserMapper.findById",
                                    fetchType = FetchType.LAZY)),
                    @Result(property = "text", column = "text")
            })
    Post findById(@Param("postId") long id);

    @Select("select * from posts where user_id = #{userId}")
    @ResultMap("postMapper")
    List<Post> findByUser(@Param("userId") long userId);

    @Insert("insert into posts(user_id, text) values (#{user.id}, #{text})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Post post);

    @Delete("delete from posts where id = #{id}")
    void delete(Post post);
}
