package com.example.skydog.controller;

import com.example.skydog.module.entity.Category;
import com.example.skydog.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * Author:甘舟
 * Data:2022/9/7
 */

@RestController
@RequestMapping("/category")
@Api(tags = "商品种类管理控制器")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 根据Id查询
     * @param categoryId
     * @return
     */
    @GetMapping("/queryById/{categoryId}")
    @ApiOperation("id查询商品种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "商品种类id", dataType = "Int"),
    })
    public Category queryById(@PathVariable Integer categoryId) {
        Category category = categoryService.queryId(categoryId);
        return category;
    }

    /**
     * 添加商品
     * @param category
     * @return
     */
    @GetMapping("/add")
    @ApiOperation("添加商品种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "商品种类", dataType = "Category"),
    })
    public Category add(@RequestBody Category category) {
        categoryService.add(category);
        return category;
    }
    /**
     * 删除商品
     * @param categoryId
     * @return
     */
    @GetMapping("/delete/{categoryId}")
    @ApiOperation("删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "商品种类id", dataType = "Int"),
    })
    public Object delete(@PathVariable Integer categoryId) {
        categoryService.delete(categoryId);
        return null;
    }

    /**
     * 修改商品
     * @param category
     * @return
     */
    @GetMapping("/update")
    @ApiOperation("修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", value = "产品", dataType = "Product"),
    })
    public Category update(@RequestBody Category category) {
        categoryService.update(category);
        return category;
    }
    /**
     * 模糊查询商品种类
     * @param category
     * @return
     */
    @PostMapping("/queryCondition")
    @ApiOperation("模糊查询商品种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "商品种类", dataType = "Category"),
    })
    public Object queryCondition(@RequestBody Category category) {
        List<Category> products =categoryService.queryCondition(category);
        return products;
    }

    /**
     * 查询所有商品种类
     * @param
     * @return
     */
    @GetMapping("/getCategory")
    @ApiOperation("查询所有商品种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "商品种类", dataType = "Category"),
    })
    public Object getCategory() {
        Category category = new Category();
        List<Category> products =categoryService.queryCondition(category);
        return products;
    }
}
