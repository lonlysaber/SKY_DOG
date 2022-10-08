package com.example.skydog.dao; /***********************************************************************
 * Module:  Interface_OrderDaoImpl.java
 * Author:  86159
 * Purpose: Defines the Interface Interface_OrderDaoImpl
 ***********************************************************************/

import com.example.skydog.module.entity.Order;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.OrderVo;
import com.example.skydog.module.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;


import java.util.*;

@Mapper
public interface OrderDao {

   void add(Order order);

   void delete(int orderId);

   List<Order> queryId(int userId);

   List<Order> queryCondition(Order order);

   void update(Order order);

   //void batchAdd(List<Order> orderList);

   void batchDelete(List list);


   List<Order> getMyOrder(Order order);

   List<Order> queryBySelectActive(OrderVo orderVo);

   Integer countBySelectActive(OrderVo orderVo);

}