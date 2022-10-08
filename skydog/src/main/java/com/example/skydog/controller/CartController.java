package com.example.skydog.controller;

import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Cart;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:甘舟
 * Data:2022/9/7
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "购物车管理控制器")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 根据Id查询
     * @param cartId
     * @return
     */
    @GetMapping("/queryById/{cartId}")
    @ApiOperation("id查询购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "购物车id", dataType = "Int"),
    })
    public ResultVO queryById(@PathVariable Integer cartId) {
        return cartService.queryId(cartId);
    }

    /**
     * 添加商品
     * @param cart
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cart", value = "购物车", dataType = "cart"),
    })
    public ResultVO add(@RequestBody Cart cart) {
        return cartService.add(cart);
    }
    /**
     * 删除购物车
     * @param cartId
     * @return
     */
    @GetMapping("/delete/{cartId}")
    @ApiOperation("删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "购物车id", dataType = "Int"),
    })
    public Object delete(@PathVariable Integer cartId) {
        cartService.delete(cartId);
        return null;
    }

//    /**
//     * 修改购物车
//     * @param cart
//     * @return
//     */
//    @GetMapping("/add")
//    @ApiOperation("修改购物车")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "product", value = "产品", dataType = "Product"),
//    })
//    public Cart update(@RequestBody Cart cart) {
//        cartService.update(cart);
//        return cart;
//    }
//    /**
//     * 模糊查询商品种类
//     * @param cart
//     * @return
//     */
//    @GetMapping("/queryCondition")
//    @ApiOperation("模糊查询购物车")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "cart", value = "商品种类", dataType = "Category"),
//    })
//    public Object queryById(@RequestBody Cart cart) {
//        List<Cart> carts =cartService.queryCondition(cart);
//        return carts;
//    }
    /**
     * 获取用户购物车
     * @param userId
     * @return
     */
    @GetMapping("/getMyCart/{userId}")
    @ApiOperation("获取用户购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Int"),
    })
    public ResultVO getMyCart(@PathVariable Integer userId) {
        return cartService.getMyCart(userId);
    }
}
