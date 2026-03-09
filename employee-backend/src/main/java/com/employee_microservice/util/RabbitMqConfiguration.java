package com.employee_microservice.util;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

  public String queue_name = "employee.create";

  @Bean
  Queue queueName() {
    return new Queue(queue_name);
  }

}
