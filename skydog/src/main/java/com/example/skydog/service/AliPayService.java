package com.example.skydog.service;

import com.alipay.api.AlipayApiException;
import com.example.skydog.module.vo.AlipayBean;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface AliPayService {

    /**
     * 支付宝支付接口
     *
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;

    /**
     * 同步跳转
     */
    void returnUrl(HttpServletRequest request) throws UnsupportedEncodingException;

    /**
     * 异步通知
     */
    String notifyUrl(HttpServletRequest request);

    /**
     * 交易查询
     **/
    String queryOrder(String outTradeNo) throws AlipayApiException;
}
