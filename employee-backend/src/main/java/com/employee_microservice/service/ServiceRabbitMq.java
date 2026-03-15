package com.employee_microservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.DirectExchange;
import com.employee_microservice.model.dto.MessageRabbitMq;
import com.employee_microservice.service.intefacesService.InterfaceServiceRabbit;

@Service
public class ServiceRabbitMq implements InterfaceServiceRabbit {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private DirectExchange direct;

  @Override
  public void senMessageAndBrokerRabbitMq(MessageRabbitMq mRabbitMq) {
    rabbitTemplate.convertAndSend(direct.getName(), "employee.delete", mRabbitMq.toString().getBytes());
    rabbitTemplate.convertAndSend(direct.getName(), "employee.save", mRabbitMq.toString().getBytes());
  }

}
