package com.employee_microservice.model.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

  @Id
  @Column(name = "id_employee")
  private Long idEmployee;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "name_one", nullable = false, length = 100)
  private String nameOne;

  @Column(name = "other_name", length = 100)
  private String otherName;

  @Column(name = "first_surname", nullable = false, length = 100)
  private String firstSurname;

  @Column(name = "second_surname", nullable = false, length = 100)
  private String secondSurname;

  @Column(nullable = false, length = 20)
  private String telephone;

  @Column(length = 150)
  private String address;

  private Integer postcode;

  @Column(name = "city_name", nullable = false)
  private String cityName;

  @Column(name = "position_name", nullable = false)
  private String positionName;

  @Column(name = "id_department", nullable = false)
  private Long idDepartment;
}
