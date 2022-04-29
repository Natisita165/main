package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.plaf.PanelUI;

/**
 * @author jrojas
 */
@Configuration
public class RabbitMqConfig {

    public static final String QUEUE = "test_queue";
    public static final String EXCHANGE = "test_exchange";
    public static final String EXCHANGE2 = "test_exchange2";
    public static final String EXCHANGE3 = "test_exchange3";
    public static final String ROUTING_KEY = "test_routing_key";
    public static final String ROUTING_KEY2 = "queue.producer";
    public static final String TOPICEXCHANGE = "test_topicExchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchange(){return new TopicExchange(TOPICEXCHANGE);}


    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange exchange2() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    public FanoutExchange exchange3() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    Queue ProducerQueue() {
        return new Queue("ProducerQueue", false);
    }

    @Bean
    Queue StudentQueue() {
        return new Queue("StudentQueue", false);
    }

    @Bean
    Binding StudentBinding(Queue StudentQueue, FanoutExchange exchange2) {
        return BindingBuilder.bind(StudentQueue).to(exchange2);
    }
    @Bean
    Binding ProducerBinding(Queue ProducerQueue, FanoutExchange exchange3) {
        return BindingBuilder.bind(ProducerQueue).to(exchange3);
    }

    @Bean
    Binding prodBinding(Queue ProducerQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(ProducerQueue).to(topicExchange).with("queue.producer");
    }

}
