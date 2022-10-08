package com.example.skydog.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:甘舟
 * Data:2022/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Img {
    private Integer imgId;
    private Integer productId;
    private String productName;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
}
