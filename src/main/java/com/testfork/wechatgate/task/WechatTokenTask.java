package com.testfork.wechatgate.task;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xingshulin
 * @Date: 2018/12/26 下午6:45
 * @Description: 定时刷新微信accesstoken任务
 * @Version: 1.0
 **/
@EnableScheduling
@Component
public class WechatTokenTask {
    private static Logger logger = LoggerFactory.getLogger(WechatTokenTask.class);

    @Value("${wechat.access_token.url}")
    private String tokenUrl;
    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedRate = 90 * 60 * 1000)
    private void RefreshAccessToken() {
        logger.info("开始刷新微信TOKEN:请求URL-{}", tokenUrl);
        String result = restTemplate.getForObject(tokenUrl, String.class);
        logger.info("获取access_token接口响应结果：{}", result);
        try {
            JSONObject token = JSONObject.parseObject(result);
            String accessToken = token.getString("access_token");
            if (StringUtils.hasText(accessToken)) {
                logger.info("获取access_token成功，写入缓存：{}", accessToken);

                return;
            }
            logger.error("获取access_token失败,微信接口返回结果{}", token.toJSONString());
        } catch (Exception je) {
            logger.error("解析微信返回结果异常", je);
        }
    }
}
