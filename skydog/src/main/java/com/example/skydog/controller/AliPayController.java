package com.example.skydog.controller;

import com.alipay.api.AlipayApiException;
import com.example.skydog.module.vo.AlipayBean;
import com.example.skydog.service.AliPayService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @description: 沙箱支付
 * @author: 张振彬
 * @create: 2022-09-17
 * @version: 1.0
 */
@Controller
@RequestMapping(value = "/alipay", produces = {"text/html;charset=UTF-8"})
public class AliPayController {


    @Autowired
    private AliPayService aliPayService;


    /**
     * 支付二维码生成
     */
    @PostMapping(value = "/orderPay", produces = "text/html;charset=utf-8")
    @ApiOperation("支付订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outTradeNo", value = "订单编号", dataType = "String"),
            @ApiImplicitParam(name = "subject", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "totalAmount", value = "付款金额", dataType = "String"),
            @ApiImplicitParam(name = "body", value = "商品描述（可为空）", dataType = "String"),
    })
    @ResponseBody
    public String orderPay(String outTradeNo, String subject, String totalAmount, String body) throws AlipayApiException {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        System.out.println(outTradeNo);
        System.out.println(subject);
        System.out.println(totalAmount);
        System.out.println(body);
        return aliPayService.aliPay(alipayBean);
    }


    /**
     * 支付跳转，判断二维码支付接口是否调用成功
     **/
    @GetMapping(value = "/returnUrl")
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        aliPayService.returnUrl(request);
        return "me";
    }


    /**
     * 支付宝异步通知，支付宝支付之后调用改接口
     */
    @PostMapping(value = "/notifyUrl")
    public String notifyUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String orderNumber = aliPayService.notifyUrl(request);
        if (!orderNumber.equals("fail")) {
            System.out.println("【订单已完成】" + "orderNumber");
        } else {
            System.out.println("【订单待支付】");
        }
        return orderNumber;
    }


    /**
     * 交易状态查询
     * */
    @GetMapping(value = "/requestQuery")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outTradeNo", value = "订单编号", dataType = "String")
    })
    @ResponseBody
    public String requestQuery(String outTradeNo) throws AlipayApiException {
        return aliPayService.queryOrder(outTradeNo);
    }
}



