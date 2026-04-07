package com.employee_microservice.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employee_microservice.api.MicroserviceDepartment;
import com.employee_microservice.exception.DeleteEmployeeExceptions;
import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.dto.MessageRabbitDeleteEmployee;
import com.employee_microservice.model.dto.MessageRabbitMq;
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

  @Autowired
  private ServiceRabbitMq serviceRabbitMq;

  @Override
  public EmployeeDtoResponse getEmployeeForID(Long id) {
    Optional<Employee> employee = Employeerepository.findById(id);
    return mapperEmployee.getEmployee(employee.get());
  }

  @Override
  public Optional<EmployeeDtoResponse> saveEmployee(EmployeeDtoRequest employeeDto)
      throws IOException, InterruptedException {
    String url = uri + "/department/search/" + employeeDto.department_id() + "/";
    boolean response = apiMicroservice.dataMicroserviceDepartment(url);
    Optional<EmployeeDtoResponse> optional;
    if (response) {
      Employee employee = mapperEmployee.getDtoToEmployee(employeeDto);
      optional = Optional.ofNullable(mapperEmployee.getEmployee(Employeerepository.save(employee)));
      sendMessageToBroker(employee);
    } else {
      optional = Optional.empty();
    }
    return optional;
  }

  @Override
  public void sendMessageToBroker(Employee employee) {
    MessageRabbitMq messageRabbitMq = new MessageRabbitMq(employee.getIdEmployee(),
        employee.getNameOne() + employee.getOtherName() + employee.getFirstSurname() + employee.getSecondSurname(),
        employee.getEmail(), employee.getIdDepartment(), LocalDateTime.now());
    serviceRabbitMq.senMessageAndBrokerRabbitMq(messageRabbitMq);
  }

  @Override
  public void deleteEmployee(Long id_employee) throws DeleteEmployeeExceptions {
    Optional<Employee> get_employee = Employeerepository.findById(id_employee);
    if (get_employee.isEmpty())
      throw new DeleteEmployeeExceptions("Employee not found ");
    Employee emplo = get_employee.get();
    Employeerepository.delete(emplo);
    send_message_delete_employee(emplo);
  }

  @Override
  public void send_message_delete_employee(Employee emplo) {
    String name_complete_employee = emplo.getFirstSurname() + " " + emplo.getOtherName() + " " + emplo.getFirstSurname()
        + " " + emplo.getSecondSurname();

    MessageRabbitDeleteEmployee mDeleteEmployee = new MessageRabbitDeleteEmployee(emplo.getIdEmployee(),
        name_complete_employee, emplo.getEmail());
    serviceRabbitMq.sendMessageDeleteEmployee(mDeleteEmployee);
  }

  public List<EmployeeDtoResponse> get_all_employee() {
    List<Employee> list_employees = Employeerepository.findAll();
    if (!list_employees.isEmpty()) {
    }
    return mapperEmployee.return_employee_list_employee_dto_response(list_employees);

  }

}
