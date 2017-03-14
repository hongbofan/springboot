package com.example.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Created by yfmacmini001 on 2017/3/7.
 */
//@Configurable
//public class RabbitMQConfig {

    //    /** 消息交换机的名字*/
//    public static final String EXCHANGE = "my-mq-exchange";
//    /** 队列key1*/
//    public static final String ROUTINGKEY1 = "queue_one_key1";
//    /** 队列key2*/
//    public static final String ROUTINGKEY2 = "queue_one_key2";
//
//    /**
//     * 配置链接信息
//     * @return
//     */
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("115.28.160.104",5672);
//
//        connectionFactory.setUsername("hongbofan");
//        connectionFactory.setPassword("h350559165");
//        connectionFactory.setVirtualHost("/");
//        connectionFactory.setPublisherConfirms(true); // 必须要设置
//        return connectionFactory;
//    }
//
//    /**
//     * 配置消息交换机
//     * 针对消费者配置
//     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
//     HeadersExchange ：通过添加属性key-value匹配
//     DirectExchange:按照routingkey分发到指定队列
//     TopicExchange:多关键字匹配
//     */
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(EXCHANGE, true, false);
//    }
//    /**
//     * 配置消息队列1
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        return new Queue("queue_one", true); //队列持久
//
//    }
//    /**
//     * 将消息队列1与交换机绑定
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY1);
//    }
//
//    /**
//     * 配置消息队列2
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public Queue queue1() {
//        return new Queue("queue_one1", true); //队列持久
//
//    }
//    /**
//     * 将消息队列2与交换机绑定
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public Binding binding1() {
//        return BindingBuilder.bind(queue1()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY2);
//    }
//    /**
//     * 接受消息的监听，这个监听会接受消息队列1的消息
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//
//            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("收到消息 : " + new String(body));
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//
//            }
//
//        });
//        return container;
//    }
//    /**
//     * 接受消息的监听，这个监听会接受消息队列1的消息
//     * 针对消费者配置
//     * @return
//     */
//    @Bean
//    public SimpleMessageListenerContainer messageContainer2() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue1());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//
//            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("queue1 收到消息 : " + new String(body));
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//
//            }
//
//        });
//        return container;
//    }
//    public static final String EXCHANGE = "spring-boot-exchange";
//    public static final String ROUTINGKEY = "spring-boot-routingKey";
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses("115.28.160.104:5672");
//        connectionFactory.setUsername("hongbofan");
//        connectionFactory.setPassword("h350559165");
//        connectionFactory.setVirtualHost("/");
//        connectionFactory.setPublisherConfirms(true); //必须要设置
//        return connectionFactory;
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    //必须是prototype类型
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory());
//        return template;
//    }
//
//    /**
//     * 针对消费者配置
//     * 1. 设置交换机类型
//     * 2. 将队列绑定到交换机
//     * <p>
//     * <p>
//     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
//     * HeadersExchange ：通过添加属性key-value匹配
//     * DirectExchange:按照routingkey分发到指定队列
//     * TopicExchange:多关键字匹配
//     */
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(EXCHANGE);
//    }
//
//    @Bean
//    public Queue queue() {
//        return new Queue("hello", true); //队列持久
//
//    }
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY);
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("receive msg : " + new String(body));
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
//            }
//        });
//        return container;
//    }
//}
