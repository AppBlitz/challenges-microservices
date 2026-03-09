package com.employee_microservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.employee_microservice.model.dto.MessageRabbitMq;
import com.employee_microservice.util.RabbitMqConfiguration;

public class serviceRabbitMq {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void senMessageAndBrokerRabbitMq(MessageRabbitMq messageRabbitMq, String name_queue) {
    RabbitMqConfiguration rabbitMqConfiguration = new RabbitMqConfiguration();
    rabbitTemplate.convertAndSend(rabbitMqConfiguration.queue_name, messageRabbitMq);
  }

}
