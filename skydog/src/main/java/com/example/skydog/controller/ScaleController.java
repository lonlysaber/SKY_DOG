package com.example.skydog.controller;

import com.example.skydog.module.entity.Product;
import com.example.skydog.module.entity.Scale;
import com.example.skydog.service.ScaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scale")
@Api(tags = "规格管理控制器")
public class ScaleController {
    @Autowired
    ScaleService scaleService;

    /**
     * 添加规格
     * @param scale
     * @return
     */
    @GetMapping("/add")
    @ApiOperation("添加规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scale", value = "规格", dataType = "Scale"),
    })
    public Scale add(@RequestBody Scale scale) {
        scaleService.add(scale);
        return scale;
    }

    /*
    * 删除规格
    * */
    @GetMapping("/delete/{scaleId}")
    @ApiOperation("删除规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scaleId", value = "规格id", dataType = "Int"),
    })
    public Object delete(@PathVariable Integer scaleId) {
        scaleService.delete(scaleId);
        return true;
    }
    /*
    修改规格
     */
    @GetMapping("/update")
    @ApiOperation("修改规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scale", value = "规格", dataType = "Scale"),
    })
    public Scale update(@RequestBody Scale scale) {
        scaleService.update(scale);
        return scale;
    }
    /*
    按id查询
     */
    @GetMapping("/queryById/{scaleId}")
    @ApiOperation("id查询规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scaleId", value = "规格id", dataType = "Int"),
    })
    public Scale queryById(@PathVariable Integer scaleId) {
        Scale scale =scaleService.queryId(scaleId);
        return scale;
    }
}
