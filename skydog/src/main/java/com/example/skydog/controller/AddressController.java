package com.example.skydog.controller;

import com.example.skydog.dao.AddressDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Address;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.AddressService;
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
@RequestMapping("/address")
@Api(tags = "地址前端控制器")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    @ApiOperation("添加地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址", dataType = "Address"),
    })
    public ResultVO addAddress(@RequestBody Address address) {

        return addressService.add((address));
    }

    @GetMapping("/delete/{addressId}")
    @ApiOperation("删除地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "地址Id", dataType = "Integer"),
    })
    public ResultVO deleteAddress(@PathVariable Integer addressId) {
        return addressService.delete(addressId);
    }

    @PostMapping("/updateAddress")
    @ApiOperation("地址更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址", dataType = "Address"),
    })
    public ResultVO updateAddress(@RequestBody Address address) {
        return addressService.update((address));
    }


    @GetMapping("/queryId/{addressId}")
    @ApiOperation("Id查询地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "地址Id", dataType = "Integer"),
    })
    public ResultVO queryId(@PathVariable Integer addressId) {
        Address address = addressService.queryId(addressId);

        return new ResultVO(ResultEnum.SUCCESS, address);
    }


    @GetMapping("/getMyAddress/{userId}")
    @ApiOperation("查询用户地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getMyAddress(@PathVariable Integer userId) {

        return addressService.getMyAddress(userId);
    }


}
