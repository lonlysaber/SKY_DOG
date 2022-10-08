package com.example.skydog.dao;

import com.example.skydog.module.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
@Mapper
public interface CartDao {

   Integer add(Cart cart);

   void batchAdd(List<Cart> carts);

   void update(Cart cart);

   void batchDelete(List list);

   void delete(Integer cartId);

   Cart queryId(Integer cartId);

   List<Cart> queryCondition(Cart cart);

   List<Cart> getMyCart(Integer userId);

}