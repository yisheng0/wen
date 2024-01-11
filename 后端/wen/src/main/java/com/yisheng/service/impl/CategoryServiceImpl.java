package com.yisheng.service.impl;

import com.yisheng.mapper.CategoryMapper;
import com.yisheng.pojo.Category;
import com.yisheng.service.CategoryService;
import com.yisheng.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //补充属性
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        category.setCreateUser((Integer) map.get("id"));

        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
    Map<String,Object> map = ThreadLocalUtil.get();
    List<Category> cs =  categoryMapper.list((Integer)map.get("id"));
    return cs;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category findById(Integer id) {
        categoryMapper.findById(id);
        return null;
    }
}
