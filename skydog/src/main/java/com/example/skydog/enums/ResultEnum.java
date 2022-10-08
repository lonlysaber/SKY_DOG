package com.example.skydog.enums;


import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    ADD_SUCCESS(202, "添加成功"),
    ADD_FAIL(203, "添加失败"),
    UPDATE_SUCCESS(204, "修改成功"),
    UPDATE_FAIL(205, "修改失败"),
    DELETE_SUCCESS(206, "删除成功"),
    DELETE_FAIL(207, "删除失败"),
    SERVICE_ERROR(500, "服务异常");

    private Integer code;

    private String message;

    private ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
