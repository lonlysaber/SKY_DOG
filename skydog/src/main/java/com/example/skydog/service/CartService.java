package com.example.skydog.service;

import com.example.skydog.module.entity.Cart;
import com.example.skydog.module.vo.ResultVO;

/**
 * Author:甘舟
 * Data:2022/9/7
 */

public interface CartService {

   ResultVO add(Cart cart);

   void update(Cart cart);

   void delete(Integer cartId);

   ResultVO queryId(Integer cartId);

   ResultVO queryCondition(Cart cart);

   ResultVO getMyCart(Integer userId);
}