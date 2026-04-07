package com.employee_microservice.service.intefacesService;

import com.employee_microservice.model.dto.MessageRabbitDeleteEmployee;
import com.employee_microservice.model.dto.MessageRabbitMq;
import com.employee_microservice.model.entitys.Employee;

public interface InterfaceServiceRabbit {
  public void senMessageAndBrokerRabbitMq(MessageRabbitMq mRabbitMq);

  public void sendMessageDeleteEmployee(MessageRabbitDeleteEmployee mDeleteEmployee);

}
