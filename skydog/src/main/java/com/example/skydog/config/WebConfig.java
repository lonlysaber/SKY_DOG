package com.example.skydog.config;


import com.example.skydog.utils.DateFormatter;
import com.example.skydog.utils.StringFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 跨域请求
     * @param registry
     */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                //.allowedMethods(new String[]{"GET", "POST"})
                .allowedMethods("*") //或者放行全部
                //放行哪些原始请求头部信息
                .allowedHeaders("*")
                //暴露哪些原始请求头部信息
                .exposedHeaders("*");
    }
    /**
     * 添加格式化转换器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new StringFormatter());
        registry.addFormatter(new DateFormatter());
    }
}

