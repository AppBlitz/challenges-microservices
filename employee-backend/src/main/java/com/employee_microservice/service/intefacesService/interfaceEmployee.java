package com.employee_microservice.service.intefacesService;

import java.io.IOException;
import java.util.Optional;

import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.entitys.Employee;

public interface interfaceEmployee {
  /**
   * @param id
   * @return Employee
   *         Method that receives the employee's unique identifier as a parameter
   *         and returns EmployeeDtoResponse
   */
  public EmployeeDtoResponse getEmployeeForID(Long id);

  /**
   * @param employeeDto
   * @return Employee
   *         Method that receives a DTO class and saves the employee in the
   *         database
   */

  public Optional<EmployeeDtoResponse> saveEmployee(EmployeeDtoRequest employeeDto)
      throws IOException, InterruptedException;

  public void sendMessageToBroker(Employee employee);
}
