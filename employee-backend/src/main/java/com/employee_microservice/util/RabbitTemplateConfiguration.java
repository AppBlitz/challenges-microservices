package com.employee_microservice.util;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfiguration {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("employee.events");
    }

    @Bean
    public Queue createQueue() {
        return new Queue("employee");
    }

    @Bean
    public Binding binding1a(DirectExchange direct,
            Queue saveEmployeeQueue) {
        return BindingBuilder.bind(saveEmployeeQueue)
                .to(direct)
                .with("employee.save");
    }

    @Bean
    public Binding binding2a(DirectExchange direct, Queue deleteEmployee) {
        return BindingBuilder.bind(deleteEmployee)
                .to(direct)
                .with("employee.delete");
    }

}
