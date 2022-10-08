package com.example.skydog.service;

import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;

/**
 * Author:甘舟
 * Data:2022/9/23
 */
public interface RecommendService {
    ResultVO hotRecommend(ProductVo productVo);
}
