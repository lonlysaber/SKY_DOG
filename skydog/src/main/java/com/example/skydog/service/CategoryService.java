package com.example.skydog.service;

import com.example.skydog.module.entity.Category;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */

public interface CategoryService {

   void add(Category category);

   void delete(Integer categoryId);

   void update(Category category);

   Category queryId(Integer categoryId);

   List<Category> queryCondition(Category category);

}