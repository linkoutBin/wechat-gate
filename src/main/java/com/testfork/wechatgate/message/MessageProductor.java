package com.testfork.wechatgate.message;

import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Author: xingshulin Date: 2019/2/22 下午7:33
 *
 *
 * Description: TODO Version: 1.0
 **/
@Configuration
public class MessageProductor {

  /* @Bean
   public ConnectionFactory connectionFactory() {
     CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
     connectionFactory.setAddresses("192.168.64.65:5672,192.168.64.68:5672");
     connectionFactory.setUsername("guest");
     connectionFactory.setPassword("guest");
     connectionFactory.setVirtualHost("uas_user");
     connectionFactory.setPublisherConfirms(true); //必须要设置
     return connectionFactory;
   }

   @Bean
   @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
   public RabbitTemplate rabbitTemplate() {
     return new RabbitTemplate(connectionFactory());
   }
 */
  public static void main(String[] args) throws IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setConnectionTimeout(2000);
    factory.setHost("192.168.20.100");
    factory.setPort(5672); //默认端口号
    factory.setUsername("guest");//默认用户名
    factory.setPassword("guest");//默认密码
    factory.setVirtualHost("uas_user");
    Connection connection = null;
    Channel channel = null;
    try {
      connection = factory.newConnection();
      channel = connection.createChannel();
      channel.queueDeclare("paymentNotify_queue", true, false, false, null);
      String message = "Hello world";
      channel.basicPublish("amq.direct", "paymentNotify_route_key", null,
          message.getBytes());
      channel.queueBind("paymentNotify_queue","amq.direct","paymentNotify_route_key");
      System.out.println(" [x] Sent '" + message + "'");
    } catch (TimeoutException e) {
      e.printStackTrace();
    } finally {
      try {
        channel.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      connection.close();
    }
    // 接下来，我们创建一个channel，绝大部分API方法需要通过调用它来完成。
    // 发送之前，我们必须声明消息要发往哪个队列，然后我们可以向队列发一条消息：
  }


}
