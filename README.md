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
