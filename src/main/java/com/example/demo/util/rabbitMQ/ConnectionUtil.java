package com.example.demo.util.rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

    
    public static Connection getConnection(String user,String pwd) throws IOException, TimeoutException{
      //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置RabbitMQ相关信息
        factory.setHost("192.168.18.100");
        factory.setUsername(user);
        factory.setPassword(pwd);
        factory.setPort(5672);

        //创建一个新的连接
        return factory.newConnection();
    }
}
