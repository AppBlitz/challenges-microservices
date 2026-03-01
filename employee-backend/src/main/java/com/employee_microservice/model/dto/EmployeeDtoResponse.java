package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

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
