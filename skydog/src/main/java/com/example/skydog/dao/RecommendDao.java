package com.example.skydog.dao;

import com.example.skydog.module.dto.ProductDto;
import com.example.skydog.module.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/23
 */
@Mapper
public interface RecommendDao {
    List<ProductDto> hotRecommend(ProductVo productVo);
    Integer countRecommend(ProductVo productVo);
}
