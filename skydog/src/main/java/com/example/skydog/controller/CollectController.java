package com.example.skydog.controller;

import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Address;
import com.example.skydog.module.entity.Collect;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.CollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */
@RestController
@RequestMapping("/collect")
@Api(tags = "收藏管理控制器")
public class CollectController {

    @Autowired
    private CollectService collectService;


    /**
     * 商品收藏
     *
     * @param collect
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collect", value = "收藏对象", dataType = "Collect"),
    })
    public ResultVO addProduct(@RequestBody Collect collect) {

        return collectService.add(collect);
    }

    /**
     * 店铺收藏
     *
     * @param collect
     * @return
     */
    @PostMapping("/addStore")
    @ApiOperation("店铺收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collect", value = "收藏对象", dataType = "Collect"),
    })
    public ResultVO addStore(@RequestBody Collect collect) {

        return collectService.addStore(collect);
    }



    @GetMapping("/delete/{userId}&&{productId}")
    @ApiOperation("删除收藏商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
            @ApiImplicitParam(name = "productId", value = "商品Id", dataType = "Integer"),
    })
    public ResultVO deleteProduct(@PathVariable Integer userId, @PathVariable Integer productId) {
        return collectService.delete(userId, productId);
    }


    @GetMapping("/deleteStore/{userId}&&{sellerId}")
    @ApiOperation("删除收藏商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
            @ApiImplicitParam(name = "sellerId", value = "店铺Id", dataType = "Integer"),
    })
    public ResultVO deleteStore(@PathVariable Integer userId, @PathVariable Integer sellerId) {
        return collectService.deleteStore(userId, sellerId);
    }

    /**
     * 查询收藏列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/getMyCollect/{userId}")
    @ApiOperation("查询商品收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getMyCollect(@PathVariable Integer userId) {
        return collectService.getMyCollect((userId));
    }


    /**
     * 查询收藏列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/getMyStore/{userId}")
    @ApiOperation("查询店铺收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getMyStore(@PathVariable Integer userId) {
        return collectService.getMyStore((userId));
    }




    /**
     * 动态查询列表商品
     *
     * @param collect
     * @return
     */
    @GetMapping("/queryCondition")
    @ApiOperation("动态查询列表商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collect", value = "收藏对象", dataType = "Collect"),
    })
    public ResultVO queryCondition(@RequestBody Collect collect) {
        collectService.queryCondition(collect);
        return new ResultVO(collectService.queryCondition(collect));
    }


    /**
     * 查询收藏列表
     *
     * @param collectId
     * @return
     */
    @GetMapping("/queryId/{collectId}")
    @ApiOperation("Id查询收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectId", value = "收藏Id", dataType = "Integer"),
    })
    public ResultVO queryId(@PathVariable Integer collectId) {
        return collectService.queryId((collectId));
    }


    /**
     * 收藏失效商品
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUseless/{userId}")
    @ApiOperation("查询收藏列表失效商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getUseless(@PathVariable Integer userId) {
        return collectService.getUseless((userId));
    }

    /**
     * 收藏商品搜索
     *
     * @param userId
     * @param keyword
     * @return
     */
    @GetMapping("/getSearch")
    @ApiOperation("查询收藏列表失效商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
            @ApiImplicitParam(name = "keyword", value = "关键词", dataType = "String"),
    })
    public ResultVO getSearch(Integer userId, String keyword) {
        return collectService.getSearch(userId, keyword);
    }


}
