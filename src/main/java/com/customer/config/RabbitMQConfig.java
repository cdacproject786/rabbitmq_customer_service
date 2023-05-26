package com.customer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name1.1}")
    private String ordersQueue1;

    @Value("${rabbitmq.queue.name1.2}")
    private String ordersQueue2;

    @Value("${rabbitmq.queue.name2}")
    private String orderOutQueue;

    @Value("${rabbitmq.queue.name3}")
    private String orderDeliveredQueue;

    @Value("${rabbitmq.binding.key3}")
    private String orderDeliveredKey;

    @Value("${rabbitmq.exchange.name}")
    private String ordersExchange;

    @Value("${rabbitmq.binding.key1.1}")
    private String ordersBindingKey1;

    @Value("${rabbitmq.binding.key1.2}")
    private String ordersBindingKey2;

    @Value("${rabbitmq.binding.key2}")
    private String orderOutKey;

    @Bean
    public Queue ordersQueue1()
    {
        return new Queue(ordersQueue1);
    }

    @Bean
    public Queue ordersQueue2(){
        return new Queue(ordersQueue2);
    }

    @Bean
    public Queue orderOutQueue()
    {
        return new Queue(orderOutQueue);
    }

    @Bean
    public Queue orderDeliveredQueue()
    {
        return new Queue(orderDeliveredQueue);
    }
    @Bean
    public TopicExchange ordersExchange1()
    {
        return new TopicExchange(ordersExchange);
    }

    @Bean
    public Binding orderDeliveredBinding()
    {
        return BindingBuilder.bind(orderDeliveredQueue())
                .to(ordersExchange1())
                .with(orderDeliveredKey);
    }
    @Bean
    public Binding orderOutBinding()
    {
        return BindingBuilder.bind(orderOutQueue())
                .to(ordersExchange1())
                .with(orderOutKey);
    }
    @Bean
    public Binding orderBinding2()
    {
        return BindingBuilder.bind(ordersQueue2())
                .to(ordersExchange1())
                .with(ordersBindingKey2);
    }

    @Bean
    public Binding ordersBinding1()
    {
        return BindingBuilder.bind(ordersQueue1())
                .to(ordersExchange1())
                .with(ordersBindingKey1);
    }
    @Bean
    public MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
