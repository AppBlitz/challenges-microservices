package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for receiving employee registration or update requests.
 * This record captures the necessary details provided by the client to create or 
 * modify an employee record.
 * * @param id_employee    The unique identifier for the employee.
 * @param name_one       The employee's primary given name.
 * @param other_name     The employee's middle or secondary name.
 * @param first_surname  The employee's primary family name.
 * @param second_surname The employee's secondary family name.
 * @param telephone      The contact phone number, constrained to exactly 10 characters.
 * @param address        The residential or mailing address.
 * @param postcode       The numerical postal or zip code.
 * @param email          The employee's email address, validated for correct format.
 * @param city_name      The name of the city for the employee's location.
 * @param position_name  The job title or role requested for the employee.
 * @param department_id  The unique identifier of the department the employee belongs to.
 */
public record EmployeeDtoRequest(
        long id_employee,
        String name_one,
        String other_name,
        String first_surname,
        String second_surname,
        @Size(min = 10, max = 10) String telephone,
        String address,
        Integer postcode,
        @Email String email,
        String city_name,
        String position_name,
        long department_id) {
}
