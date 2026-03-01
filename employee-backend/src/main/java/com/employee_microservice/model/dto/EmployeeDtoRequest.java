package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

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
