package com.employee_microservice.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employee_microservice.api.MicroserviceDepartment;
import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.entitys.Employee;
import com.employee_microservice.repository.EmployeeRepository;
import com.employee_microservice.service.intefacesService.interfaceEmployee;
import com.employee_microservice.util.MapperEmployee;

@Service
public class ServiceEmployee implements interfaceEmployee {

  @Value("${URI_SERVICE_D}")
  private String uri;

  @Autowired
  private EmployeeRepository Employeerepository;

  @Autowired
  MicroserviceDepartment apiMicroservice;

  @Autowired
  private MapperEmployee mapperEmployee;

  @Override
  public EmployeeDtoResponse getEmployeeForID(Long id) {
    Optional<Employee> employee = Employeerepository.findById(id);
    return mapperEmployee.getEmployee(employee.get());
  }

  @Override
  public EmployeeDtoResponse saveEmployee(EmployeeDtoRequest employeeDto) throws IOException, InterruptedException {
    String url = uri + "/department/search/" + employeeDto.department_id() + "/";
    boolean response = apiMicroservice.dataMicroserviceDepartment(url);
    if (!response) {
      return null;
    }
    Employee employee = mapperEmployee.getDtoToEmployee(employeeDto);
    return mapperEmployee.getEmployee(Employeerepository.save(employee));
  }

}
