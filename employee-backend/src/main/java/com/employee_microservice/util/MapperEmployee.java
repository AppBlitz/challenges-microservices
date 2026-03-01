package com.employee_microservice.util;

import org.springframework.stereotype.Component;

import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.entitys.Employee;

@Component
public class MapperEmployee {

        public Employee getDtoToEmployee(EmployeeDtoRequest employeeDto) {
                System.out.println(employeeDto);
                return new Employee(employeeDto.id_employee(), employeeDto.email(), employeeDto.name_one(),
                                employeeDto.other_name(), employeeDto.first_surname(), employeeDto.second_surname(),
                                employeeDto.telephone(), employeeDto.address(), employeeDto.postcode(),
                                employeeDto.city_name(), employeeDto.position_name(), employeeDto.department_id());
        }

        public EmployeeDtoRequest getEmployeeToDto(Employee employee) {

                return new EmployeeDtoRequest(
                                employee.getIdEmployee(),
                                employee.getNameOne(),
                                employee.getOtherName(),
                                employee.getFirstSurname(),
                                employee.getSecondSurname(),
                                employee.getTelephone(),
                                employee.getAddress(),
                                employee.getPostcode(),
                                employee.getEmail(),
                                employee.getCityName(),
                                employee.getPositionName(),
                                employee.getIdEmployee());
        }

        public EmployeeDtoResponse getEmployee(Employee employee) {
                return new EmployeeDtoResponse(
                                employee.getNameOne(),
                                employee.getOtherName(),
                                employee.getFirstSurname(),
                                employee.getSecondSurname(),
                                employee.getTelephone(),
                                employee.getAddress(),
                                employee.getPostcode(),
                                employee.getEmail(),
                                employee.getCityName(),
                                employee.getPositionName(),
                                employee.getIdDepartment());
        }

}
