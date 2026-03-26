package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;

/**
 * Data Transfer Object (DTO) for representing an employee's detailed information.
 * This record is used to send employee data as a response to the client.
 * * @param name_one       The employee's first name.
 * @param other_name     The employee's middle or additional name.
 * @param first_surname  The employee's primary last name.
 * @param second_surname The employee's secondary last name.
 * @param telephone      The contact phone number of the employee.
 * @param address        The physical residential address.
 * @param postcode       The postal or zip code for the location.
 * @param email          The electronic mail address, validated by {@link Email}.
 * @param city_name      The name of the city where the employee resides.
 * @param position_name  The title or role of the employee within the company.
 * @param department_id  The unique identifier of the associated department.
 */
public record EmployeeDtoResponse(
        String name_one,
        String other_name,
        String first_surname,
        String second_surname,
        String telephone,
        String address,
        Integer postcode,
        @Email String email,
        String city_name,
        String position_name,
        Long department_id) {
}
