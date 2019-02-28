package com.testfork.wechatgate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: xingshulin
 * @Date: 2019/1/21 下午7:05
 * @Description:
 * @Version: 1.0
 **/
@Configuration
@Slf4j
public class RedisConfig {

  @Value("${redis.sentinel.master}")
  private String master;

  @Value("${redis.sentinel.nodes}")
  private String nodes;

  @Value("${redis.sentinel.password}")
  private String password;

  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
    stringRedisTemplate.afterPropertiesSet();
    return stringRedisTemplate;
  }

  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.afterPropertiesSet();
    setSerializer(redisTemplate);
    return redisTemplate;
  }

  private void setSerializer(RedisTemplate<String, String> redisTemplate) {
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
        Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
  }

  @Bean
  public RedisConnectionFactory jedisConnectionFactory() {
    log.info("实例化redis工厂类：master：{},nodes:{},passwd:{}", master, nodes, password);
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
    String[] sentinels = nodes.split(",");
    List<RedisNode> nodeList = new ArrayList<>();
    for (String sentinel : sentinels) {
      String[] node = sentinel.split(":");
      nodeList.add(RedisNode.newRedisNode().listeningAt(node[0], Integer.valueOf(node[1])).build());
    }
    redisSentinelConfiguration.master(master).setSentinels(nodeList);

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
        redisSentinelConfiguration);
    jedisConnectionFactory.setPassword(password);
    return jedisConnectionFactory;
  }
}
