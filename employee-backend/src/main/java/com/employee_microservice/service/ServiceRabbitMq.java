package com.employee_microservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.DirectExchange;

import com.employee_microservice.model.dto.MessageRabbitDeleteEmployee;
import com.employee_microservice.model.dto.MessageRabbitMq;
import com.employee_microservice.service.intefacesService.InterfaceServiceRabbit;
import org.json.JSONObject;

@Service
public class ServiceRabbitMq implements InterfaceServiceRabbit {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private DirectExchange direct;

  @Override
  public void senMessageAndBrokerRabbitMq(MessageRabbitMq mRabbitMq) {

    JSONObject object = new JSONObject();
    object.put("id", mRabbitMq.id());
    object.put("nameUser", mRabbitMq.nameUser());
    object.put("email", mRabbitMq.email());
    object.put("departmentID", mRabbitMq.departmentID());
    object.put("dateEnter", mRabbitMq.dateEnter());
    rabbitTemplate.convertAndSend(direct.getName(), "employee.save", object.toString().getBytes());
  }

  @Override
  public void sendMessageDeleteEmployee(MessageRabbitDeleteEmployee mDeleteEmployee) {
    JSONObject object = new JSONObject();
    object.put("id_employee", mDeleteEmployee.id_employee());
    object.put("name_employee", mDeleteEmployee.name_employee());
    object.put("email_employee", mDeleteEmployee.email_employee());
    rabbitTemplate.convertAndSend(direct.getName(), "employee.delete", object.toString().getBytes());
  }

}
