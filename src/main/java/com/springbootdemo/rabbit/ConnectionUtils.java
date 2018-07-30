package com.springbootdemo.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    private static ConnectionFactory factory = null;

    public static Connection getConnection(){
        if (factory==null){
            //创建连接工厂
            factory = new ConnectionFactory();
            //设置服务地址
            factory.setHost("47.106.85.65");
            //设置端口
            factory.setPort(5672);
            //设置账号信息，用户名，密码，vhost
            factory.setVirtualHost("/vhost_rabbit");
            factory.setUsername("zouzf");
            factory.setPassword("zouzf");
        }
        //通过工厂获取连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
