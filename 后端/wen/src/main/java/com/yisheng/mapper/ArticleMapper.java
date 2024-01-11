package com.yisheng.mapper;

import com.yisheng.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> list(Integer userId, Integer categoryId, String state);

    //新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    @Update("update article set id=#{id},title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},update_time =#{updateTime} where create_user=#{createUser}")
    void update(Article article);

    @Delete("delete from article where id=#{id}")
    void delete(Integer id);

    @Select("select * from article where id=#{id}")
    Article findById(Integer id);
}
