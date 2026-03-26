package com.employee_microservice.util;

import org.springframework.stereotype.Component;
import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.entitys.Employee;

/**
 * Utility component responsible for mapping data between Employee entities 
 * and various Data Transfer Objects (DTOs).
 * <p>This class ensures that the internal domain model is decoupled from 
 * the external API layer.</p>
 */
@Component
public class MapperEmployee {

    /**
     * Converts an {@link EmployeeDtoRequest} into an {@link Employee} entity.
     * * @param employeeDto The request data received from the client.
     * @return A new {@link Employee} entity populated with the DTO data.
     */
    public Employee getDtoToEmployee(EmployeeDtoRequest employeeDto) {
        return new Employee(employeeDto.id_employee(), employeeDto.email(), employeeDto.name_one(),
                employeeDto.other_name(), employeeDto.first_surname(), employeeDto.second_surname(),
                employeeDto.telephone(), employeeDto.address(), employeeDto.postcode(),
                employeeDto.city_name(), employeeDto.position_name(), employeeDto.department_id());
    }

    /**
     * Converts an {@link Employee} entity back into an {@link EmployeeDtoRequest}.
     * * @param employee The employee entity from the database.
     * @return An {@link EmployeeDtoRequest} containing the entity's details.
     */
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

    /**
     * Maps an {@link Employee} entity to an {@link EmployeeDtoResponse} for API output.
     * * @param employee The employee entity containing full data.
     * @return A filtered {@link EmployeeDtoResponse} suitable for the client response.
     */
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
