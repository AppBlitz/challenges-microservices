package com.employee_microservice.util;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ messaging components.
 * This class defines the Exchange, Queue, and Bindings required to route 
 * messages within the microservice ecosystem.
 */
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

    /**
     * Creates a Direct Exchange where messages are routed based on a specific routing key.
     * @return A configured {@link DirectExchange} instance.
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange(direct_exchange.toString());
    }

    /**
     * Defines the main Queue where employee-related messages will be stored.
     * @return A non-durable {@link Queue} instance.
     */
    @Bean
    public Queue createQueue() {
        return new Queue(name_queue.toString(), false);
    }

    /**
     * Binds the queue to the direct exchange using the 'event_one' routing key.
     * This is typically used for "save" or "create" events.
     * @param direct The defined Direct Exchange.
     * @param saveEmployeeQueue The target Queue.
     * @return A {@link Binding} between the exchange and queue.
     */
    @Bean
    public Binding binding1a(DirectExchange direct,
            Queue saveEmployeeQueue) {
        return BindingBuilder.bind(saveEmployeeQueue)
                .to(direct)
                .with(event_one.toString());
    }

    /**
     * Binds the queue to the direct exchange using the 'event_two' routing key.
     * This is typically used for "delete" events.
     * @param direct The defined Direct Exchange.
     * @param deleteEmployee The target Queue.
     * @return A {@link Binding} between the exchange and queue.
     */
    @Bean
    public Binding binding2a(DirectExchange direct, Queue deleteEmployee) {
        return BindingBuilder.bind(deleteEmployee)
                .to(direct)
                .with(event_two.toString());
    }
}
