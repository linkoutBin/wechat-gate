package com.testfork.wechatgate.task;

import com.alibaba.fastjson.JSONObject;
import com.testfork.wechatgate.dao.WechatDao;
import com.testfork.wechatgate.domain.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WechatDao wechatDao;

    @Value("${wechat.access_token.url}")
    private String tokenUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedRate = 90 * 60 * 1000)
    private void RefreshAccessToken() {
        logger.info("定时执行刷新微信TOKEN任务:请求URL-{}", tokenUrl);
        List<AppInfo> appInfos = wechatDao.getAppInfos();
        if (appInfos.isEmpty()) {
            logger.error("未找到需要刷新token的公众号信息");
            return;
        }
        for (AppInfo appInfo : appInfos) {
            if (checkAppInfo(appInfo)) {
                tokenUrl = String.format(tokenUrl, appInfo.getAppId(), appInfo.getSecret());
                String result = restTemplate.getForObject(tokenUrl, String.class);
                logger.info("获取access_token接口响应结果：{}", result);
                try {
                    JSONObject token = JSONObject.parseObject(result);
                    String accessToken = token.getString("access_token");
                    if (StringUtils.hasText(accessToken)) {
                        long timeout = token.getLong("expires_in");
                        stringRedisTemplate.opsForValue().set(appInfo.getKey(), accessToken, timeout, TimeUnit.SECONDS);
                        logger.info("成功获取access_token并写入缓存");
                    }
                } catch (Exception je) {
                    logger.error("解析微信返回结果异常", je);
                }
            }
        }
    }

    private boolean checkAppInfo(AppInfo appInfo) {
        if (StringUtils.isEmpty(appInfo.getAppId()) || StringUtils.isEmpty(appInfo.getSecret()) || StringUtils.isEmpty(appInfo.getKey())) {
            logger.error("公众号配置信息不全，跳过刷新token:{}", appInfo.toString());
            return false;
        }
        return true;
    }
}
