package com.employee_microservice.service.intefacesService;

import java.io.IOException;

import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;

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

  public EmployeeDtoResponse saveEmployee(EmployeeDtoRequest employeeDto) throws IOException, InterruptedException;
}
