package com.testfork.wechatgate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.testfork.wechatgate.dao.WechatDao;
import com.testfork.wechatgate.dao.WechatJadDAO;
import com.testfork.wechatgate.domain.AppInfo;
import com.testfork.wechatgate.singleton.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/2/19 下午2:44
 *
 *
 * Description: 集合测试类 Version: 1.0
 **/

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class CollectionTest {

  private static final String EXCHANGE = "paymentNotify_exchange";
  private static final String ROUTINGKEY = "paymentNotify_route_key";

  @Autowired
  private WechatJadDAO wechatJadDAO;

  @Autowired
  private WechatDao wechatDao;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private RedisTemplate redisTemplate;


  @Test
  public void testRedis() {
    AppInfo appInfo = new AppInfo();
    appInfo.setId("a");
    appInfo.setWd("wd");
    redisTemplate.opsForValue().set("appInfo", appInfo);
    redisTemplate.opsForValue().set("key", "value");
    Map<String, Object> map = new HashMap<>();
    map.put("field", "value");
    map.put("field1", "value1");
    map.put("field2", "value2");
    redisTemplate.opsForHash().putAll("hkey", map);
    map = redisTemplate.opsForHash().entries("hkey");
    redisTemplate.opsForList().leftPush("list", appInfo);
    redisTemplate.opsForList().set("list", 0, appInfo);
    redisTemplate.opsForSet().add("set", new Object[]{1, 2, 3});
    redisTemplate.opsForZSet().add("zset", appInfo, 1.0);//有序集合 根据分数排序
    String value = (String) redisTemplate.opsForValue().get("key");
    AppInfo appInfo1 = (AppInfo) redisTemplate.opsForValue().get("appInfo");
    log.info("获取到的值：appInfo:{},key:{},map:{}", appInfo1, value, map);
  }

  @Test
  public void messageTest() {
    rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, "heheheh");
    log.info("发送完成");
  }

  @Test
  public void queueTest() {
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    queue.add(1);
    queue.add(2);
    queue.add(3);
    queue.add(4);
    queue.add(5);
    queue.peek();
    queue.element();
  }

  @Test
  public void listTest() {
    LinkedList<Integer> list = new LinkedList<>();
    list.add(1);
    Integer num = list.getFirst();
    log.info(num.toString());
  }

  @Test
  public void jadTest() {
    List<Integer> ids = wechatJadDAO.getBankIds(1);
    log.info("结果：{}", ids);
    Singleton singleton = Singleton.getInstance();
    singleton.print("hello");
    Singleton singleton1 = Singleton.getInstance();
    singleton1.print("world");

  }

  @Test
  public void jdbcTest() {
    List<AppInfo> appInfos = wechatDao.getAppInfos();
    log.info(appInfos.toString());
  }

  public static void main(String[] args) {
    StopWatch watch = new StopWatch();
    watch.start("计算时间");
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    for (int j = 0; j < 2; j++) {
      executorService.execute(() -> {
        for (int i = 0; i < 10; i++) {
          try {
            Thread.sleep(1000);
            log.info(Thread.currentThread().getName() + ":" + i);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    try {
      executorService.shutdown();
      executorService.awaitTermination(11000, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("测试结束");
    watch.stop();

    log.info(String.valueOf(watch.getTotalTimeSeconds()));
  }

}
