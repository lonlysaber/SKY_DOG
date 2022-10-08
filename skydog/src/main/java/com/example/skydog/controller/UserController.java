package com.example.skydog.controller;

import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.entity.Address;
import com.example.skydog.module.entity.User;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.module.vo.UserVO;
import com.example.skydog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:用户前端控制器
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户前端控制器")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResultVO login(@RequestBody User user) {
        return userService.login(user);
    }


    @PostMapping("/register")
    public ResultVO register(@RequestBody User user) {
        return userService.register(user);
    }


    @PostMapping("/updateClient")
    @ApiOperation("修改个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户对象", dataType = "User"),
    })
    public ResultVO updateClient(@RequestBody User user){
        return this.userService.update(user);
    }



    @GetMapping("/updatePwd")
    @ApiOperation("用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO updatePwd(String oldPassword, String newPassword, Integer userId){
        return this.userService.updatePassword(oldPassword,newPassword,userId);
    }



    @GetMapping("/queryId/{userId}")
            @ApiOperation("Id查询用户")
            @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO queryId(@PathVariable Integer userId) {
        return new ResultVO(ResultEnum.SUCCESS, userService.queryId(userId));
    }



    @PostMapping("/getUser")
    @ApiOperation("查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVO", value = "用户", dataType = "UserVO"),
    })
    public ResultVO getUser(@RequestBody UserVO userVO) {
        return userService.getUser(userVO);
    }




    @GetMapping("/getUserRecommend/{userId}")
    @ApiOperation("获取用户个性推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer"),
    })
    public ResultVO getUserRecommend(@PathVariable Integer userId) {
        return new ResultVO(ResultEnum.SUCCESS, userService.getUserRecommend(userId));
    }

}
