package com.example.skydog.module.entity;

import com.example.skydog.module.dto.ProductDto;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;
/**
 * Author:付贵平，甘舟
 * Data:2022/9/7
 */
@Data//含有set,get方法
@AllArgsConstructor//含有参构造方法
@NoArgsConstructor//含无参构造方法
public class Order implements Serializable {
    private Integer orderId;
    private Integer userId;
    private Integer cartId;
    private Integer addressId;
    private Integer productId;
    private Integer scaleId;
    private Integer productCount;
    private String orderStatus;
    private Date createTime;
    private Date payTime;
    private Date endTime;
    private ProductDto productDto;
    private Address address;
}
