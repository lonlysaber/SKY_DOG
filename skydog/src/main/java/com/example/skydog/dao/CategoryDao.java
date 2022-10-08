package com.example.skydog.dao;


import com.example.skydog.module.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
@Mapper
public interface CategoryDao {

   void add(Category category);

   void batchAdd(List<Category> categories);

   void delete(Integer categoryId);

   void batchDelete(List list);

   void update(Category category);

   List queryCondition(Category category);

   Category queryId(Integer categoryId);

}