package com.example.skydog.module.dto;

import com.example.skydog.module.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:甘舟
 * Data:2022/9/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Product {
    private Double grade;
    private String categoryName;
    private String productScale;
    private String productScaleStock;
    private Double productScalePrice;
}
