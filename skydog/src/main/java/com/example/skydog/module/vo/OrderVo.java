package com.example.skydog.module.vo;

import com.example.skydog.module.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author:甘舟
 * Data:2022/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo extends Order {
    private Date beginDate;
    private Date endDate;
    private Integer currentPage;
    private Integer pageSize;
}
