package com.yisheng.service;

import com.yisheng.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    void update(Category category);

    void delete(Integer id);

    Category findById(Integer id);
}
