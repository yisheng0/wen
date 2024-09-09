package com.yisheng.mapper;

import com.yisheng.pojo.Session;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SessionMapper {
    @Insert("insert into session(create_user,title,content,create_time,update_time)" +
            "values(#{createUser},#{title},#{content},#{createTime},#{updateTime})")
    void add(Session session);
}
