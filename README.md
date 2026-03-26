[English](#English)
[Español](#Spanish)

# Compresión - Sistema de Microservicios

Este proyecto implementa una arquitectura de microservicios para la gestión empresarial, enfocada en la interacción entre empleados y unidades organizacionales.

---

## Spanish

### Microservicio 1: Gestión de Empleados
Este servicio administra el ciclo de vida de los registros de empleados:
* **Persistencia:** Utiliza una base de datos relacional MySQL.
* **Lógica de Negocio:** Al registrar un nuevo empleado, es obligatorio asignarle un departamento existente.
* **Interacción:** El sistema realiza una solicitud HTTP sincrónica al Microservicio de Departamentos para validar su existencia.
* **Validación:** Si el departamento no se encuentra, la operación se cancela y se retorna un valor nulo al cliente para garantizar la integridad referencial.

**Requisitos:** MySQL.

### Microservicio 2: Gestión de Departamentos
Este servicio gestiona las entidades de departamentos, permitiendo su creación y consulta:
* **Persistencia:** Se seleccionó PostgreSQL por su robustez, escalabilidad y eficiencia en el manejo de datos relacionales.

**Requisitos:** PostgreSQL.

### Configuración y Despliegue
El despliegue se realiza mediante Docker Compose, permitiendo la orquestación simultánea de todos los servicios:
* **Variables de Entorno:** Es fundamental configurar correctamente las variables en el archivo docker-compose.yml.
* **Comunicación Sincrónica:** La conexión entre Empleados y Departamentos es de tipo bloqueante; el primer servicio espera la validación del segundo antes de confirmar la respuesta al usuario final.


### ¿Por qué se escogio rabbitMq?
Primodrialmente se escogio debido a su alta documentación, facilidad de uso y que no  al grupo no se le hacia tan dificíl la implementación en los diferentes lenguajes que estamos utilzando para utilizar este broker.
#### lenguajes de programación que se está utilzando
* **java:**  Java es el lenguaje de programación que se encarga de publicar los diferentes eventos 
* **javascript:** Se encarga de capturar los eventos que se lanzan y los almacena en una base de datos como un log
* **c#:** Se encarga de la captura de los eventos que lanza java y crear un usuario con los datos que recibe 

### Explicación de los json que se utilizaron para los eventos
#### Evento de guardar empleado 


```json

{
  "id":"{id employee}",
  "nameUser":"{name employee}",
  "email":"{email employee}",
  "departmentID":"{departmentID}",
  "dateEnter":"{date register employee}"
}
```

#### Evento cuando se elima un empleado email

```json

{
  "id_employee":"{id employee}",
  "name_employee":"{name employee}",
  "email":"{email employee}",
}
```
---

## English

### Microservice 1: Employee Management
This service handles the administration of employee records:
* **Persistence:** Uses a MySQL relational database.
* **Business Logic:** When creating a new employee, it is mandatory to assign them to an existing department.
* **Interaction:** The system performs a synchronous HTTP request to the Department Microservice to validate its existence.
* **Validation:** If the department does not exist, the operation is canceled, returning a null value to the client to ensure referential integrity.

**Requirements:** MySQL.

### Microservice 2: Department Management
This service manages department entities, allowing for their creation and retrieval:
* **Persistence:** PostgreSQL was selected due to its robustness, reliability, and ability to scale as entities grow.

**Requirements:** PostgreSQL.

### Configuration and Deployment
Deployment is managed through Docker Compose, allowing for the simultaneous orchestration of all services:
* **Environment Variables:** It is essential to correctly configure the environment variables within the docker-compose.yml file.
* **Synchronous Communication:** The connection between Employees and Departments is blocking (synchronous), as the first service must wait for validation from the second before confirming the final response.


## 📌 Why was RabbitMQ chosen?

RabbitMQ was primarily chosen due to its extensive documentation, ease of use, and because it was not too difficult for the team to implement across the different programming languages used in the project.

---

## 📌 Programming languages used

- **Java**: Responsible for publishing the different events.  
- **JavaScript**: Handles capturing the emitted events and storing them in a database as logs.  
- **C#**: Responsible for consuming events published by Java and creating users based on the received data.  

---

## 📌 Explanation of the JSON used for events

This section describes the structure of the JSON used to represent events in the system.

### 🧾 Example: Employee creation event



```json

{
  "id":"{id employee}",
  "nameUser":"{name employee}",
  "email":"{email employee}",
  "departmentID":"{departmentID}",
  "dateEnter":"{date register employee}"
}
```

#### When an employee is deleted

```json

{
  "id_employee":"{id employee}",
  "name_employee":"{name employee}",
  "email":"{email employee}",
}
```
