package com.example.skydog.controller;

import com.example.skydog.module.entity.Collect;
import com.example.skydog.module.entity.Evaluate;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */
@RestController
@RequestMapping("/evaluate")
@Api(tags = "评价管理控制器")
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;


    @PostMapping("/add")
    @ApiOperation("发布评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluate", value = "评论", dataType = "Evaluate"),
    })
    public ResultVO addEvaluate(@RequestBody Evaluate evaluate) {

        return evaluateService.add(evaluate);
    }


    @GetMapping("/delete/{userId}&&{productId}")
    @ApiOperation("删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
            @ApiImplicitParam(name = "evaluate", value = "评论d", dataType = "Integer"),
    })
    public ResultVO deleteEvaluate(@PathVariable Integer userId, @PathVariable Integer productId) {
        return evaluateService.delete(userId, productId);
    }

    @GetMapping("/queryId/{evaluateId}")
    @ApiOperation("Id查询评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluateId", value = "评价Id", dataType = "Integer"),
    })
    public ResultVO queryId(@PathVariable Integer evaluateId) {

        return new ResultVO(evaluateService.queryId((evaluateId)));
    }

    @GetMapping("/getMyEvaluate/{userId}")
    @ApiOperation("获取用户所有评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getMyEvaluate(@PathVariable Integer userId) {

        return new ResultVO(evaluateService.getMyEvaluate((userId)));
    }

    @GetMapping("/getProductEvaluate/{productId}")
    @ApiOperation("获取商品所有评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品Id", dataType = "Integer"),
    })
    public ResultVO getProductEvaluate(@PathVariable Integer productId) {

        return new ResultVO(evaluateService.getProductEvaluate((productId)));
    }

}
