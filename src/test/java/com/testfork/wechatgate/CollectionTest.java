package com.testfork.wechatgate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.testfork.wechatgate.dao.WechatDao;
import com.testfork.wechatgate.dao.WechatJadDAO;
import com.testfork.wechatgate.domain.AppInfo;

/**
 * Author: xingshulin Date: 2019/2/19 下午2:44
 *
 *
 * Description: 集合测试类 Version: 1.0
 **/

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CollectionTest {

  @Autowired
  private WechatJadDAO wechatJadDAO;

  @Autowired
  private WechatDao wechatDao;

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
    System.out.println(num);
  }

  @Test
  public void jadTest() {
    List<Integer> ids = wechatJadDAO.getBankIds(1);
    System.out.println(ids);
  }

  @Test
  public void jdbcTest() {
    List<AppInfo> appInfos = wechatDao.getAppInfos();
    System.out.println(appInfos);
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
            System.out.println(Thread.currentThread().getName() + ":" + i);
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
    System.out.println("测试结束");
    watch.stop();
    System.out.println(watch.getTotalTimeSeconds());
  }

}
