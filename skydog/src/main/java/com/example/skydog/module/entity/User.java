package com.example.skydog.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String password;
    private String nick;//昵称
    private String phone;
    private String avatar;//头像
    private String gender;
    private String IDCard;
    private List<Product> productList;
    private Img img;
    private Collect collect;
}
