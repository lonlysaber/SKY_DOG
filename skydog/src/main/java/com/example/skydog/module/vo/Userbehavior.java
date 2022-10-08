package com.example.skydog.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userbehavior {
    private String time;//时间搓
    private String userId;//用户ID
    private String adId;//广告ID
    private String behavior;//行为操作

}
