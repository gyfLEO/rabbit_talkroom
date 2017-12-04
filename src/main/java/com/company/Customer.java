package com.company;
/**
 * Created by gyfleo on 2017/12/1.
 */

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;


public class Customer {
    public final static String QUEUE_NAME = "rabbitMQ";
    public static final boolean durable = false;

    public static void customer() throws IOException, TimeoutException {
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

        channel.exchangeDeclare(QUEUE_NAME, "fanout", durable);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, QUEUE_NAME, "");

//        System.out.println(" [*] Waiting for messages");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        //告诉服务器我们需要哪个频道的消息，如果频道中有消息，就会执行回掉函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
            }
        };
        //自动回复队列应答（RabbitMQ中的消息确认机制）
        channel.basicConsume(queueName, true, consumer);
    }
}

