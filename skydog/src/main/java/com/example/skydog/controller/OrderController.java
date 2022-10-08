package com.example.skydog.controller;

import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Order;
import com.example.skydog.module.vo.OrderVo;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Author:付贵平，甘舟
 * Data:2022/9/7
 */
@Api(tags = "订单管理控制器")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/add")
    @ApiOperation("添加订单")
    public ResultVO add(@RequestBody Order order) throws ParseException {
        return orderService.add(order);
    }
    @GetMapping("/delete/{orderId}")
    @ApiOperation("删除订单")
    public Object delete(@PathVariable Integer orderId) {
        orderService.delete(orderId);
        return true;
    }
    @PostMapping("/update")
    @ApiOperation("修改订单信息")
    public Order update(@RequestBody Order order) {
        orderService.update(order);
        return order;
    }

    @GetMapping("/queryById/{orderId}")
    @ApiOperation("按id查询订单")
    public List<Order> queryById(@PathVariable Integer orderId) {
        return orderService.queryId(orderId);

    }

    /**
     * 获取用户订单
     * @param order
     * @return
     */
    @PostMapping("/getMyOrder")
    @ApiOperation("获取用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", dataType = "Order"),
    })
    public ResultVO getMyOrder(@RequestBody Order order) {
        return new ResultVO(ResultEnum.SUCCESS,orderService.getMyOrder(order));
    }

    /**
     * 条件分页查询订单
     * @param orderVo
     * @return
     */
    @PostMapping("/getOrder")
    @ApiOperation("条件分页查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderVo", value = "订单对象", dataType = "OrderVo"),
    })
    public ResultVO getMyOrder(@RequestBody OrderVo orderVo) {
        return orderService.getOrder(orderVo);
    }
}
