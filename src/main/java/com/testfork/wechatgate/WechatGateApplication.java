package com.testfork.wechatgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xubin
 * @Date: 2018/12/26 下午6:36
 * @Description: 微信网关启动类入口
 * @Version: 1.0
 **/
@SpringBootApplication
@ImportResource(locations = {"classpath:applicationContext-jade.xml"})
public class WechatGateApplication {

  public static void main(String[] args) {
    SpringApplication.run(WechatGateApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(1000);
    requestFactory.setReadTimeout(1000);
    return new RestTemplate(requestFactory);

  }

}
