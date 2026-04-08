package com.employee_microservice.controller;

import java.util.List;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_microservice.exception.DeleteEmployeeExceptions;
import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.service.ServiceEmployee;
import com.employee_microservice.util.MapperEmployee;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class ControllerEmployee {

  @Autowired
  ServiceEmployee serviceEmployee;

  @Autowired
  MapperEmployee mapperEmployee;

  @GetMapping(path = "/retrieve/{idEmployee}", produces = "application/json")
  public ResponseEntity<EmployeeDtoResponse> retrieveEmployee(@PathVariable("idEmployee") Long idEmployee) {

    return ResponseEntity.ok((serviceEmployee.getEmployeeForID(idEmployee)));
  }

  @PostMapping(path = "/save/", produces = "application/json")
  public ResponseEntity<EmployeeDtoResponse> updateEmployee(@Valid @RequestBody EmployeeDtoRequest employeeDto)
      throws IOException, InterruptedException {
    Optional<EmployeeDtoResponse> response = serviceEmployee.saveEmployee(employeeDto);
    if (response.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    return ResponseEntity.ok(response.get());
  }

  @GetMapping(path = "/all/", produces = "application/json")
  public ResponseEntity<List<EmployeeDtoResponse>> get_all_employees() {
    List<EmployeeDtoResponse> list_employees = serviceEmployee.get_all_employee();
    if (list_employees.isEmpty()) {
      return ResponseEntity.status(204).body(list_employees);
    } else {
      return ResponseEntity.status(200).body(list_employees);
    }
  }

  @DeleteMapping("/delete/{id_employee_delete}")
  public ResponseEntity<String> delete_employee(@PathVariable() Long id_employee_delete)
      throws DeleteEmployeeExceptions {
    serviceEmployee.deleteEmployee(id_employee_delete);
    return ResponseEntity.status(204).body("the employee is succesfull delete");
  }

}
