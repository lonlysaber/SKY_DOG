package com.example.skydog.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect implements Serializable {
    private Integer collectId;
    private Integer userId;
    private Integer productId;
    private Date collectTime;
    private Product product;
    private Img img;
    private Integer sellerId;
    private User user;
    private List<Product> productList;
}
