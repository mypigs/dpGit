package com.example.demo.controller.rabbitMQ.p2p;

import java.io.IOException;
import com.example.demo.util.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class Customer {

    private final static String QUEUE_NAME = "p2p_queue";  
    private final static Boolean AUTO_ACK = false;  //是否自动回复 false：手动回复
    
    public static void getMsg1() throws Exception {  
  
        // 创建一个新的连接
        Connection connection = ConnectionUtil.getConnection("duanp","duanp");
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Customer1 Waiting Received messages");

        // 每次从队列获取的数量
        //channel.basicQos(1);保证一次只分发一个
        
        // DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    if(message.equals("1次")||message.equals("2次")){
                        throw new Exception();
                    }
                    //throw new Exception();
                    doWork(message);
                    System.out.println("消费者1接收消息： '" + message + "'");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    //true：出现异常，回滚消息到队列头
                    channel.basicNack(envelope.getDeliveryTag(), true, true);
//                    channel.abort();
                }
            }
        };
        // AUTO_ACK：自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, AUTO_ACK, consumer);
    }  
    
    public static void getMsg2() throws Exception {  
        
        // 创建一个新的连接
        Connection connection = ConnectionUtil.getConnection("zixian","zixian");
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Customer2 Waiting Received messages");
        
        // 每次从队列获取的数量
        //channel.basicQos(1);保证一次只分发一个
        
        // DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                
                try {
                    //throw new Exception();
                    doWork(message);
                    System.out.println("消费者2接收消息： '" + message + "'");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    //true：出现异常，回滚消息到队列头
                    channel.basicNack(envelope.getDeliveryTag(), true, true);
//                    channel.abort();
                }
            }
        };
        // AUTO_ACK：手动回复队列应答 -- RabbitMQ中的消息确认机制  
        channel.basicConsume(QUEUE_NAME, AUTO_ACK, consumer);
    }  
    
    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
