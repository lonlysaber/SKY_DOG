package com.example.skydog.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description: 支付配置类
 * @author: 张振彬
 * @create: 2022-09-17
 * @version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "alipay")
@PropertySource("classpath:/application.yml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AlipayConfig {
    private String appId;
    private String privateKey;
    private String publicKey;
    private String notifyUrl;
    private String returnUrl;
    private String signType;
    private String charset;
    private String gatewayUrl;
    private String logPath;
}
