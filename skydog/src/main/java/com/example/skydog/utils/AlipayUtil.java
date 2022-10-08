package com.example.skydog.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.example.skydog.config.AlipayConfig;
import com.example.skydog.module.vo.AlipayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: 支付工具类
 * @author: 张振彬
 * @create: 2022-09-17
 * @version: 1.0
 */
@Component
public class AlipayUtil {


    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 支付接口
     *
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        String serverUrl = alipayConfig.getGatewayUrl();
        String appId = alipayConfig.getAppId();
        String privateKey = alipayConfig.getPrivateKey();
        String format = "json";
        String charset = alipayConfig.getCharset();
        String alipayPublicKey = alipayConfig.getPublicKey();
        String signType = alipayConfig.getSignType();
        String returnUrl = alipayConfig.getReturnUrl();
        String notifyUrl = alipayConfig.getNotifyUrl();
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        System.out.println(result);
        // 返回付款信息
        return result;
    }

    /**
     * 同步跳转
     */
    public void returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        //获取支付宝反馈信息数据
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //System.out.println("<--同步回调的值-->" + name + "\t\t" + valueStr);
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);

        //验证签名（支付宝公钥）
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        } catch (Exception e) {
            System.out.println("【异步签名异常：】" + e.getMessage());
            e.printStackTrace();
        }

        //验证签名通过
        if (signVerified) {

            System.out.println("【同步回调签名验证】" + signVerified);
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");


            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            request.setAttribute("out_trade_no", out_trade_no);
            request.setAttribute("trade_no", trade_no);
            request.setAttribute("total_amount", total_amount);

            //System.out.println("<--同步回调系统订单号-->" + out_trade_no + "\t\t<--同步回调支付宝交易号-->" + trade_no);
            //系统处理根据支付宝回调更改订单状态或者其他关联表的数据
        } else {
            request.setAttribute("reason", "验签失败");
        }
        request.setAttribute("signVerified", signVerified);

    }


    /**
     * 异步通知
     */
    public String notifyUrl(HttpServletRequest request) {
        String payStatus = "fail";
        Map<String , String > params = new HashMap<>();
        Map<String , String []> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext() ; ){
            String name = iter.next();
            String [] values = requestParams.get(name);
            String valStr = "";
            for (int i = 0 ; i < values.length ; i ++){
                valStr = (i == values.length - 1) ? valStr + values[i] : valStr + values[i] + ",";
            }
            params.put(name , valStr);
            System.out.println("【异步通知的值】" + name + "\t\t:" + valStr);
        }

        //签名验证
        boolean signVerified = false;

        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        }catch (AlipayApiException e){
            System.out.println("【异步签名异常】" + e.getErrMsg());
        }
        //签名验证通过
        if (signVerified){
            System.out.println("【异步通知签名验证】" + signVerified);
            String trade_status = request.getParameter("trade_status");//交易状态
            String out_trade_no = request.getParameter("out_trade_no");//商户订单号
            //System.out.println("【异步通知商户订单号】" + out_trade_no + "\t\t【异步通知交易状态】" + trade_status);
            if (trade_status.equals("TRADE_SUCCESS")){  //判断订单交易状态
              payStatus = out_trade_no+"已支付";
            }
        }
        return payStatus;
    }


    /**
     * 交易查询
     **/
    public String queryOrder(String outTradeNo) throws AlipayApiException {
        String format = "json";
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(),format, alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"trade_no\":\"" + "" + "\"," +
                "\"org_pid\":\"" + "" + "\"," +
                "      \"query_options\":[" +
                "        \"TRADE_SETTLE_INFO\"" +
                "      ]" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println("【查询返回交易状态】" + response.getTradeStatus());
        return "【查询返回交易状态】" + response.getTradeStatus();
    }


}
