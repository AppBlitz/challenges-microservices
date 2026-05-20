package com.authService.auth_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    //public static final String QUEUE_NAME = "${NAME_QUEUE_AUTH}";
    //public static final String EXCHANGE_NAME = "${EXCHANGE_RABBIT}";
    // public static final String ROUTING_KEY_SAVE = "${EVENT_ONE}";
    // public static final String ROUTING_KEY_DELETE = "${EVENT_TWO}";
    // public static final String ROUTING_KEY_RECUPERATION = "${EVENT_THREE}";

    @Value("${name_queue}")       // coincide con NAME_QUEUE
    private String QUEUE_NAME;

    @Value("${exchange_rabbit}")  // coincide con EXCHANGE_RABBIT
    private String EXCHANGE_NAME;

    @Value("${event_one}")        // coincide con EVENT_ONE
    private String eventOne;

    @Value("${event_two}")        // coincide con EVENT_TWO
    private String eventTwo;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue authEmployeeQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    Binding bindingSave(Queue authEmployeeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(authEmployeeQueue)
                .to(exchange)
                .with(eventOne);
    }

    @Bean
    Binding bindingDelete(Queue authEmployeeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(authEmployeeQueue)
                .to(exchange)
                .with(eventTwo);
    }

    //     @Bean
    // public Queue employeeQueue() {
    //     return new Queue(QUEUE_NAME, false); // durable = true
    // }

    // @Bean
    // public DirectExchange employeeExchange() {
    //     return new DirectExchange(EXCHANGE_NAME);
    // }

    // @Bean
    // public Queue authQueue() {
    //     return new Queue(QUEUE_NAME, false);
    // }

    // @Bean
    // public Binding bindingAuth(Queue authQueue, DirectExchange employeeExchange) {
    //     return BindingBuilder.bind(authQueue).to(employeeExchange).with("employee.save");
    // }

    // @Bean
    // public Binding bindingAuthDelete(Queue authQueue, DirectExchange employeeExchange) {
    //     return BindingBuilder.bind(authQueue).to(employeeExchange).with("employee.delete");
    // }
}
