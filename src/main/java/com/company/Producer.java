package com.company;
/**
 * Created by gyfleo on 2017/12/1.
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public final static String QUEUE_NAME = "rabbitMQ";
    public static final boolean durable = false;

    public static void producer() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory= new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("120.79.8.110");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setPort(5672);

        //创建一个新连接
        Connection connection = factory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();

        //声明一个fanout队列
//        channel.exchangeDeclare(QUEUE_NAME, "fanout");
        channel.exchangeDeclare(QUEUE_NAME, "fanout", durable);
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息到队列中
        System.out.println("Welcome to Rabbit ChatRoom ~");
        System.out.println("Type q to exit ~");
        System.out.println("Input your name：");
        String username = new Scanner(System.in).nextLine();
        System.out.println("Hello "+username+",Type q to exit ~");
//        String message = "Hello RabbitMQ";
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
//        System.out.println("Producer Send + '"+ message +"'");
        while(true) {
            String message = new Scanner(System.in).nextLine();
            if("q".equals(message)){
                //关闭连接和通道
                channel.close();
                connection.close();
                return;
            }
            message = username +":"+message;

            channel.basicPublish(QUEUE_NAME, "", null, message.getBytes());
//            System.out.println(message);
        }
//        //关闭连接和通道
//        channel.close();
//        connection.close();
    }
}

