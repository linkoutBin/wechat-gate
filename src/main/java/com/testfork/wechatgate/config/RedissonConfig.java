package com.testfork.wechatgate.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xingshulin
 * @Date: 2019/2/1 上午11:19
 * @Description: TODO
 * @Version: 1.0
 **/
@Configuration
public class RedissonConfig {

  @Value("${redis.sentinel.master}")
  private String master;

  @Value("${redis.sentinel.nodes}")
  private String nodes;

  @Value("${redis.sentinel.password}")
  private String password;

  private static RedissonClient redissonClient;

  private Config config() {
    Config config = new Config();
    config.setCodec(new StringCodec());
    config.useSentinelServers().setMasterName(master).addSentinelAddress(nodes.split(","));
    /*config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    config.useSingleServer().setPassword("123456");
    config.useSingleServer().setConnectionPoolSize(500);
    config.useSingleServer().setIdleConnectionTimeout(10000);
    config.useSingleServer().setConnectTimeout(30000);
    config.useSingleServer().setTimeout(3000);
    config.useSingleServer().setPingTimeout(30000);
    config.useSingleServer().setReconnectionTimeout(3000);*/
    //redissonClient = Redisson.create(config);
    return config;
  }

  public RedissonClient getRedissonClient() {
    return Redisson.create(config());
  }
}
