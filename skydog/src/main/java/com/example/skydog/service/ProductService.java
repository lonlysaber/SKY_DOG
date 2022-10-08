package com.example.skydog.service;

import com.example.skydog.module.entity.Category;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.PageBeans;
import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
public interface ProductService {

   ResultVO add(Product product);

   ResultVO update(Product product);

   ResultVO delete(Integer productId);

   ResultVO queryId(Integer productId);

   ResultVO queryCondition(Product product);

   ResultVO queryByPage(PageBeans pageBeans);

   ResultVO queryByCategory(ProductVo productVo);

   ResultVO getProduct(ProductVo productVo);

   ResultVO queryByPrice(ProductVo productVo);

   ResultVO queryByName(String productName);

   ResultVO search(ProductVo productVo);

   ResultVO getMyProduct(Integer userId);

}