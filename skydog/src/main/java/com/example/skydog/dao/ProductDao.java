package com.example.skydog.dao;

import com.example.skydog.module.dto.ProductDto;
import com.example.skydog.module.entity.Category;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
@Mapper
public interface ProductDao<E> {

   void add(Product product);

   void batchAdd(List<Product> products);

   void update(Product product);

   void delete(Integer productId);

   void batchDelete(List list);

   Product queryId(Integer productId);

   List<ProductDto> queryCondition(E query);

   List<ProductDto> queryByCategory(ProductVo productVo);

   Integer countByCategory(ProductVo productVo);

   List<ProductDto> queryBySelectActive(ProductVo productVo);

   Integer countBySelectActive(ProductVo productVo);

   List<ProductDto> search(ProductVo productVo);

   Integer searchCount(ProductVo productVo);

   List<ProductDto> getMyProduct (Integer userId);
}