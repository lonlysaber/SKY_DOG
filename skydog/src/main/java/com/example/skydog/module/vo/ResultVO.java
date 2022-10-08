package com.example.skydog.module.vo;

import com.example.skydog.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 数据返回封装
 * @author: 张振彬
 * @create: 2022-09-08
 * @version: 1.0
 */


@Data
public class ResultVO<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;



    public ResultVO() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMessage();
    }


    /**
     * 查询操作
     */
    public ResultVO(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMessage();
        this.data = data;
    }


    /**
     * 更新操作
     * */
    public ResultVO(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }


    public ResultVO(ResultEnum resultEnum,T data){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }





}

