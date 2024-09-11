package com.yisheng.mapper;

import com.yisheng.pojo.Session;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SessionMapper {
    @Insert("insert into session(create_user,title,content,create_time,update_time)" +
            "values(#{createUser},#{title},#{content},#{createTime},#{updateTime})")
    void add(Session session);

    @Select("select * from session where create_user = #{id}")
    List<Session> findById(Integer id);

    @Update("update session set content=#{content} where id=#{id}")
    void update(Integer id, String content);
}
