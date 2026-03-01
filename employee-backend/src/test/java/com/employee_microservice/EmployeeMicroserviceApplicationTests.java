package com.employee_microservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.employee_microservice.api.MicroserviceDepartment;
import com.employee_microservice.model.entitys.Employee;
import com.employee_microservice.repository.EmployeeRepository;
import com.employee_microservice.service.ServiceEmployee;

@SpringBootTest
class EmployeeMicroserviceApplicationTests {
  @Autowired
  ServiceEmployee serviceEmployee;

  @Autowired
  EmployeeRepository employeeRepository;

  @Test
  public void verificationCreateEmployee() {

    // Employee employee = new Employee();
    // employee.setIdEmployee(1234L);
    // employee.setNameOne("Carlos");
    // employee.setFirstSurname("Corrales");
    // employee.setSecondSurname("Zapata");
    // employee.setEmail("carlos@example.com");
    // employee.setCityName("Armenia");
    // employee.setPositionName("Developer");
    // employee.setTelephone("3001234567");
    // employee.setIdDepartment(1L);
    //
    // EmployeeDtoRequest request = new EmployeeDtoRequest(1, "david", "luis",
    // "herrera", "lopez", "3214567890",
    // "manzana 12", 678906, "david@gmail.com", "cancun", "software developer", 4);
    //
    // assertNotNull(employeeRepository.save(employee));
  }

  @Autowired
  MicroserviceDepartment microserviceDepartment;

  @Test
  public void verificationHttpClient() throws IOException, InterruptedException {
    String result = microserviceDepartment.dataMicroserviceDepartment("https://pokeapi.co/api/v2/pokemon/ditto");
    assertNotNull(result);
  }

}
