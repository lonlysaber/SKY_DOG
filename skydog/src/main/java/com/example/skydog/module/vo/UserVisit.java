package com.example.skydog.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVisit {
    private String date;//日期时间搓
    private String userId;//用户id
    private String currentUrl;//页面地址path
    private String actionTime;//点击时间
    private String duration;//停留时间
    private String searchKeyword;//搜索关键词
    private String productId1;//商品id
    private String categoryName;//商品种类
    private String orderId;//下单id
    private String productId2;//商品id
    private String orderId2;//支付id
    private String productId3;//商品id
    private String collectId;//收藏id
    private String productId4;//商品id
    private String evaluateId;//评论id
    private String startAddress;
    private String endAddress;
    private String orderStatus;
    private String productId5;//商品id
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date + "_");
        sb.append(userId + "_");
        sb.append(currentUrl + "_");
        sb.append(actionTime + "_");
        sb.append(duration + "_");
        sb.append(searchKeyword + "_");
        sb.append(productId1 + "_");
        sb.append(categoryName + "_");
        sb.append(orderId + "_");
        sb.append(productId2 + "_");
        sb.append(orderId2 + "_");
        sb.append(productId3 + "_");
        sb.append(collectId + "_");
        sb.append(productId4 + "_");
        sb.append(evaluateId + "_");
        sb.append(startAddress + "_");
        sb.append(orderStatus + "_");
        sb.append(productId5);
        return sb.toString();
    }
    public void setDate(String date) {
        if(date == null){
            date = "null";
        }else{
            Long num = Long.parseLong(date);
            Date date1 = new Date(num);
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd HH:mm:ss
            date = formatter.format(date1);
        }
        this.date = date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCurrentUrl(String currentUrl) {
        if(currentUrl == null){
            currentUrl = "null";
        }
        this.currentUrl = currentUrl;
    }

    public void setActionTime(String actionTime) {
        if(actionTime == null){
            actionTime = "null";
        }else{
            Long num = Long.parseLong(actionTime);
            Date date1 = new Date(num);
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd HH:mm:ss
            actionTime = formatter.format(date1);
        }

        this.actionTime = actionTime;
    }

    public void setDuration(String duration) {
        if(duration == null){
            duration = "null";
        }
        this.duration = duration;
    }

    public void setSearchKeyword(String searchKeyword) {
        if(searchKeyword == null){
            searchKeyword = "null";
        }
        this.searchKeyword = searchKeyword;
    }

    public void setProductId(String productId1) {
        if(productId1 == null){
            productId1 = "null";
        }
        this.productId1 = productId1;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setOrderId(String orderId) {
        if(orderId == null){
            orderId = "null";
        }
        this.orderId = orderId;
    }

    public void setProductId2(String productId2) {
        if(productId2 == null){
            productId2 = "null";
        }
        this.productId2 = productId2;
    }

    public void setOrderId2(String orderId2) {
        if(orderId2 == null){
            orderId2 = "null";
        }
        this.orderId2 = orderId2;
    }

    public void setProductId3(String productId3) {
        if(productId3 == null){
            productId3 = "null";
        }
        this.productId3 = productId3;
    }

    public void setCollectId(String collectId) {
        if(collectId == null){
            collectId = "null";
        }
        this.collectId = collectId;
    }

    public void setProductId4(String productId4) {
        if(productId4 == null){
            productId4 = "null";
        }
        this.productId4 = productId4;
    }

    public void setEvaluateId(String evaluateId) {
        if(evaluateId == null){
            evaluateId = "null";
        }
        this.evaluateId = evaluateId;
    }

    public void startAddress(String startAddress) {
        if(startAddress == null){
            startAddress = "null";
        }
        this.startAddress = startAddress;
    }

    public void endAddress(String endAddress) {
        if(endAddress == null){
            endAddress = "null";
        }
        this.endAddress = endAddress;
    }

    public void setProductId5(String productId5) {
        if(productId5 == null){
            productId5 = "null";
        }
        this.productId5 = productId5;
    }

}
