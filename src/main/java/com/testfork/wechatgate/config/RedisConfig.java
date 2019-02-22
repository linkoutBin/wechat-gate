package com.testfork.wechatgate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xingshulin
 * @Date: 2019/1/21 下午7:05
 * @Description: TODO
 * @Version: 1.0
 **/
public class RedisConfig {

  @Value("${redis.sentinel.nodes}")
  private String sentinelNode;

  @Value("${redis.sentinel.passwd}")
  private String password;

  @Value("${redis.sentinel.master}")
  private String master;

  @Bean
  public StringRedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    return new StringRedisTemplate(jedisConnectionFactory);
  }

  @Bean
  public RedisConnectionFactory jedisConnectionFactory() {
    RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
    sentinelConfiguration.master(master);
    sentinelConfiguration.setSentinels(createSentinels());
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
        sentinelConfiguration);
    if (StringUtils.isEmpty(password)) {
      jedisConnectionFactory.setPassword(password);
    }
    return jedisConnectionFactory;
  }

  private List<RedisNode> createSentinels() {
    String[] sentinelNodes = sentinelNode.split(",");
    List<RedisNode> nodes = new ArrayList<>();
    Arrays.stream(sentinelNodes).parallel().forEach(node -> {
      try {
        String[] parts = StringUtils.split(node, ":");
        Assert.state(parts.length == 2, "Must be defined as 'host:port'");
        nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
      } catch (RuntimeException ex) {
        throw new IllegalStateException("Invalid redis sentinel " + "property '" + node + "'", ex);
      }
    });
    return nodes;
  }

}
