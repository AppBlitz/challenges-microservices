package com.employee_microservice.service.intefacesService;

import com.employee_microservice.model.dto.MessageRabbitMq;

public interface InterfaceServiceRabbit {
  public void senMessageAndBrokerRabbitMq(MessageRabbitMq mRabbitMq);

}
