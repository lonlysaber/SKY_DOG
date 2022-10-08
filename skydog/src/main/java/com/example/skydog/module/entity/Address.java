package com.example.skydog.module.entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer addressId;
    private Integer userId;
    private String consigneeName;//收货人姓名
    private String  consigneePhone;//收货人电话
    private String zone;
    private String fullAddress;
    private String isDefault;
}
