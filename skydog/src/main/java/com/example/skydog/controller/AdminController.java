package com.example.skydog.controller;

import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Admin;
import com.example.skydog.module.entity.Scale;
import com.example.skydog.module.entity.User;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员控制器")
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @GetMapping("/add")
    @ApiOperation("添加管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin", value = "管理员", dataType = "Admin"),
    })
    public ResultVO add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new ResultVO(ResultEnum.ADD_SUCCESS);
    }
    /*
     * 删除管理员
     * */
    @GetMapping("/delete/{adminId}")
    @ApiOperation("删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", dataType = "Int"),
    })
    public ResultVO delete(@PathVariable Integer adminId) {
        adminService.delete(adminId);
        return new ResultVO(ResultEnum.DELETE_SUCCESS);
    }
    /*
   修改管理员信息
    */
    @GetMapping("/update")
    @ApiOperation("修改管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin", value = "管理员", dataType = "Admin"),
    })
    public ResultVO update(@RequestBody Admin admin) {
        adminService.update(admin);
        return new ResultVO(ResultEnum.UPDATE_SUCCESS);
    }
    /*
  按id查询
   */
    @GetMapping("/queryById/{adminId}")
    @ApiOperation("id查询管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", dataType = "Int"),
    })
    public ResultVO queryById(@PathVariable Integer adminId) {
        Admin admin =adminService.queryId(adminId);
        return new ResultVO(ResultEnum.DELETE_SUCCESS);
    }

    /**
     * 登入模块
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody Admin admin) {
//        ResultVO resultVO = new ResultVO();
//        resultVO.ResultVOa();
        return adminService.login(admin);
    }

}
