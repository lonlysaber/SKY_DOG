package com.example.skydog.service.impl;


import com.example.skydog.dao.ProductDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Category;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.PageBeans;
import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

   @Autowired
   private ProductDao productDao;

   /**
    * 添加商品
    * @param product
    */
   public ResultVO add(Product product) {

      productDao.add(product);
      return new ResultVO(ResultEnum.ADD_SUCCESS,product.getProductId());
   }

   /**
    * 修改商品
    * @param product
    */
   public ResultVO update(Product product) {
      productDao.update(product);
      List<Product> products = productDao.queryCondition(product);
      //判断下是否修改成功
      if(products.isEmpty()){
         return new ResultVO(ResultEnum.UPDATE_FAIL);
      }else {
         //成功就把修改后的数据返回
         return new ResultVO(ResultEnum.UPDATE_SUCCESS,products.get(0));
      }

   }

   /**
    * 删除商品
    * @param productId
    */
   public ResultVO delete(Integer productId) {
      productDao.delete(productId);
      return new ResultVO(ResultEnum.DELETE_SUCCESS);
   }

   /**
    * 通过id查询单个商品
    * @param productId
    * @return
    */
   public ResultVO queryId(Integer productId) {
      Product product = productDao.queryId(productId);
      if(product.getProductId()==null){
         return new ResultVO(ResultEnum.FAIL);
      }else {
         return new ResultVO(ResultEnum.SUCCESS,product);
      }

   }

   /**
    * 动态模糊查询商品
    * @param product
    * @return
    */
   public ResultVO queryCondition(Product product) {
      List<Product> products = productDao.queryCondition(product);
      if(products.isEmpty()){
         return new ResultVO(ResultEnum.SUCCESS,"未找到相关商品");
      }else {
         return new ResultVO(ResultEnum.SUCCESS,products);
      }

   }
   /**
    * 动态模糊查询商品
    * @param productVo
    * @return
    */
   public ResultVO getProduct(ProductVo productVo) {
      PageBeans pageBeans = new PageBeans();
      pageBeans.setCurrentPage(productVo.getCurrentPage());
      pageBeans.setPageSize(productVo.getPageSize());
      pageBeans.setCount(productDao.countBySelectActive(productVo));
      pageBeans.setData(productDao.queryBySelectActive(productVo));
      if(pageBeans.getData().isEmpty()){
         return new ResultVO(ResultEnum.SUCCESS,"未找到相关商品");
      }else {
         return new ResultVO(ResultEnum.SUCCESS,pageBeans);
      }

   }
   public ResultVO getMyProduct(Integer userId) {
      return new ResultVO(ResultEnum.SUCCESS,productDao.getMyProduct(userId));
   }

   public ResultVO queryByPage(PageBeans pageBeans){
      return null;
   }


   public ResultVO queryByCategory(ProductVo productVo) {
      System.out.println(productVo);
      PageBeans pageBeans = new PageBeans();
      pageBeans.setCurrentPage(productVo.getCurrentPage());
      pageBeans.setPageSize(productVo.getPageSize());
      pageBeans.setCount(productDao.countByCategory(productVo));
      pageBeans.setData(productDao.queryByCategory(productVo));
      return new ResultVO(ResultEnum.SUCCESS,pageBeans);
   }

   public ResultVO queryByPrice(ProductVo productVo) {
      return new ResultVO(ResultEnum.SUCCESS,productDao.queryBySelectActive(productVo));
   }

   /**
    * 根据商品名查询
    * @param productName
    * @return
    */
   public ResultVO queryByName(String productName) {
      Product product = new Product();
      product.setProductName(productName);
      if(productDao.queryCondition(product).isEmpty()){
         return new ResultVO(ResultEnum.FAIL,"未查询到商品");
      }else{
         return new ResultVO(ResultEnum.SUCCESS,productDao.queryCondition(product));
      }
   }

   /**
    * 简单的搜索
    * @param productVo
    * @return
    */
   public ResultVO search(ProductVo productVo){
      if(productVo.getKeyword()==null || productVo.getKeyword().equals("") || productVo.getKeyword().length()==0){
         productVo.setPageSize(30);
         productVo.setCurrentPage(1);
      }
      PageBeans pageBeans = new PageBeans();
      pageBeans.setCurrentPage(productVo.getCurrentPage());
      pageBeans.setPageSize(productVo.getPageSize());
      pageBeans.setCount(productDao.searchCount(productVo));
      pageBeans.setData(productDao.search(productVo));
      return new ResultVO(ResultEnum.SUCCESS,pageBeans);
   }

}