package com.example.skydog.controller;

import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:甘舟
 * Data:2022/9/23
 */
@Api(tags = "推荐模块")
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    /**
     * 查询热门商品
     * @return
     */
    @PostMapping("/hotRec")
    @ApiOperation("查询热门商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productVo", value = "商品", dataType = "ProductVo"),
    })
    public ResultVO hotRec(@RequestBody ProductVo productVo) {
        return recommendService.hotRecommend(productVo);
    }
    /**
     * 用户推荐
     * @return
     */
    @PostMapping("/userRec")
    @ApiOperation("查询热门商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productVo", value = "商品", dataType = "ProductVo"),
    })
    public ResultVO userRec(@RequestBody ProductVo productVo) {
        return recommendService.hotRecommend(productVo);
    }

}
