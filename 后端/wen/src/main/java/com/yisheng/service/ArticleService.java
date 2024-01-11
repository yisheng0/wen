package com.yisheng.service;

import com.yisheng.pojo.Article;
import com.yisheng.pojo.PageBean;


public interface ArticleService {
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void add(Article article);

    Article findById(final Integer id);

    void update(Article article);
    void delete(Integer id);
}
