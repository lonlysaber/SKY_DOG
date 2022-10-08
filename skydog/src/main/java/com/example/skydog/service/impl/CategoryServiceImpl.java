package com.example.skydog.service.impl;

import com.example.skydog.dao.CategoryDao;
import com.example.skydog.module.entity.Category;
import com.example.skydog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

   @Autowired
   private CategoryDao categoryDao;

   /**
    * 添加商品种类
    * @param category
    */
   public void add(Category category) {
      Category category1 = new Category();
      category1.setCategoryName(category.getCategoryName());
      if(categoryDao.queryCondition(category1).isEmpty()){
         categoryDao.add(category);
      }else {

      }

   }

   /**
    * 删除商品种类
    * @param categoryId
    */
   public void delete(Integer categoryId) {
      categoryDao.delete(categoryId);
   }

   /**
    * 修改商品种类
    * @param category
    */
   public void update(Category category) {
      categoryDao.update(category);
   }

   /**
    * 通过id查询商品种类
    * @param categoryId
    * @return
    */
   public Category queryId(Integer categoryId) {
      return categoryDao.queryId(categoryId);
   }

   /**
    * 模糊查询商品种类
    * @param category
    * @return
    */
   public List<Category> queryCondition(Category category) {
      return categoryDao.queryCondition(category);
   }

}