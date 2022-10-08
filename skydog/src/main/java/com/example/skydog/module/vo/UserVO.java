package com.example.skydog.module.vo;

import com.example.skydog.module.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:甘舟
 * Data:2022/9/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends User {
    private Integer currentPage;
    private Integer pageSize;
}
