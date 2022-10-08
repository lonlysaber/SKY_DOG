package com.example.skydog.service.impl;

import com.alipay.api.AlipayApiException;
import com.example.skydog.module.vo.AlipayBean;
import com.example.skydog.service.AliPayService;
import com.example.skydog.utils.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-19
 * @version: 1.0
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private AlipayUtil alipayUtil;


    /**
     * 支付宝支付接口
     *
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {

        return alipayUtil.pay(alipayBean);
    }


    /**
     * 同步跳转
     */
    @Override
    public void returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {

        alipayUtil.returnUrl(request);
    }


    /**
     * 异步通知
     */
    @Override
    public String notifyUrl(HttpServletRequest request) {
        return alipayUtil.notifyUrl(request);
    }


    /**
     * 交易查询
     **/
    @Override
    public String queryOrder(String outTradeNo) throws AlipayApiException {
        return alipayUtil.queryOrder(outTradeNo);
    }
}
