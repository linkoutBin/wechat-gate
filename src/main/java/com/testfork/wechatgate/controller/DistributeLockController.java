package com.testfork.wechatgate.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;
import com.testfork.wechatgate.config.RedissonConfig;
import com.testfork.wechatgate.domain.ResultInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: xingshulin
 * @Date: 2019/2/1 下午12:04
 * @Description: TODO
 * @Version: 1.0
 **/
@RestController
@RequestMapping("resource/")
@Slf4j
public class DistributeLockController {

  private final static String LOCK_NAME = "SAME:";

  @Autowired
  private RedissonConfig redissonConfig;

  @RequestMapping("same/{id}")
  public ResultInfo forSameResource(@PathVariable("id") Integer id) {
    log.info("流程开始");
    RedissonClient redissonClient = redissonConfig.getRedissonClient();
    RLock lock = redissonClient.getLock(LOCK_NAME + id);
    lock.lock(1, TimeUnit.SECONDS);
    log.info("加锁成功");
    try {
      Thread.sleep(30000);
    } catch (Exception e) {
      log.error("线程中断异常", e);
    }
    lock.unlock();
    log.info("流程结束，解锁完成");
    return new ResultInfo();
  }

}
