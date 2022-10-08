package com.example.skydog.service.impl;

import com.example.skydog.dao.ProductDao;
import com.example.skydog.dao.RecommendDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.dto.ProductDto;
import com.example.skydog.module.entity.Product;
import com.example.skydog.module.vo.PageBeans;
import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Author:甘舟
 * Data:2022/9/23
 */

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private RecommendDao recommendDao;
    public ResultVO hotRecommend(ProductVo productVo){

//        List<ProductDto> productList =  productDao.queryBySelectActive(new ProductVo());
//        for (ProductDto productDto:productList){
//            productDto.setGrade((productDto.getProductSale() * 0.3/5000+
//                    productDto.getClickRate()*0.4/200000 +
//                    productDto.getProductGrade()*0.15/5+
//                    productDto.getFavorRate()*0.15/100)*100);
//        }
//        Stream<ProductDto> hotRecommend=
//                productList.stream().sorted(Comparator.comparing(ProductDto::getGrade).reversed());
        PageBeans pageBeans = new PageBeans();
        pageBeans.setCurrentPage(productVo.getCurrentPage());
        pageBeans.setPageSize(productVo.getPageSize());
        pageBeans.setCount(recommendDao.countRecommend(productVo));
        pageBeans.setData(recommendDao.hotRecommend(productVo));
        return new ResultVO(ResultEnum.SUCCESS,pageBeans);
    }

}
