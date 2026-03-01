CREATE TABLE employees(
  id_employee INTEGER PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  name_one VARCHAR(100) NOT NULL,
  other_name VARCHAR(100),
  first_surname VARCHAR(100) NOT NULL,
  second_surname VARCHAR(100) NOT NULL,
  telephone VARCHAR(20) NOT NULL,
  address VARCHAR(150),
  postcode INTEGER,
  city_name VARCHAR(255) NOT NULL,
  position_name VARCHAR(255) NOT NULL,
  id_department INTEGER
);
