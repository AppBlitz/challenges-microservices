package com.employee_microservice.util;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfiguration {

    @Value("${spring.rabbitmq.name_queue}")
    private String name_queue;

    @Value("${spring.rabbitmq.direct_exchange}")
    private String direct_exchange;

    @Value("${spring.rabbitmq.event_two}")
    private String event_two;

    @Value("${spring.rabbitmq.event_one}")
    private String event_one;

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(direct_exchange.toString());
    }

    @Bean
    public Queue createQueue() {
        return new Queue(name_queue.toString(), false);
    }

    @Bean
    public Binding binding1a(DirectExchange direct,
            Queue saveEmployeeQueue) {
        return BindingBuilder.bind(saveEmployeeQueue)
                .to(direct)
                .with(event_one.toString());
    }

    @Bean
    public Binding binding2a(DirectExchange direct, Queue deleteEmployee) {
        return BindingBuilder.bind(deleteEmployee)
                .to(direct)
                .with(event_two.toString());
    }

}
