package com.testfork.wechatgate.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: xingshulin Date: 2019/3/7 下午4:45
 *
 *
 * Description: TODO Version: 1.0
 **/
public class ConcurrentUtil {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);


    Future<Integer> result = executorService.submit(new Task(10));
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Future<Integer> result1 = executorService.submit(new Task(10));
    executorService.shutdown();

    System.out.println(Thread.currentThread().getName() + ":" + "main thread working");

    try {
      System.out.println(Thread.currentThread().getName() + ":" + "task result:" + result.get());
      System.out.println(Thread.currentThread().getName() + ":" + "task result:" + result1.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println(Thread.currentThread().getName() + ":" + "任务完成");
  }

  static class Task implements Callable<Integer> {

    private Integer num;

    public Task(Integer num) {
      this.num = num;
    }

    @Override
    public Integer call() throws Exception {
      System.out.println(Thread.currentThread().getName() + ":" + "start calculating");
      Thread.sleep(10000);
      int sum = 0;
      while (num > 0) {
        sum += num;
        num--;
      }
      return sum;
    }
  }
}
