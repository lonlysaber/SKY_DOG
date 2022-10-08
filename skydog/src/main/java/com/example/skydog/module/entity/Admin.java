package com.example.skydog.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//含有set,get方法
@AllArgsConstructor//含有参构造方法
@NoArgsConstructor//含无参构造方法
public class Admin {
    private Integer adminId;
    private String  adminName;
    private String  adminAcc;
    private String  adminPwd;

}
