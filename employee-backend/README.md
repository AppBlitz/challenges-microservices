# Spanish
Este microservicio es el encargado de toda la gestión de empleados, como la eliminación actualización y la busqueda de los mismos. También se encarga de la integración de rabbitMQ (message broker), donde envia una acción por algunas funciones  que se ejecutan en el transcurso de vida de código.

---
### Microservicio 1: Gestión de Empleados
Este servicio administra el ciclo de vida de los registros de empleados:
* **Persistencia:** Utiliza una base de datos relacional MySQL.
* **Lógica de Negocio:** Al registrar un nuevo empleado, es obligatorio asignarle un departamento existente.
* **Interacción:** El sistema realiza una solicitud HTTP sincrónica al Microservicio de Departamentos para validar su existencia.
* **Validación:** Si el departamento no se encuentra, la operación se cancela y se retorna un valor nulo al cliente para garantizar la integridad referencial.
---
### **Requisitos:**
- Mysql
---
### ¿Por qué se escogio rabbitMq?
Primodrialmente se escogio debido a su alta documentación, facilidad de uso y que no  al grupo no se le hacia tan dificíl la implementación en los diferentes lenguajes que estamos utilzando para utilizar este broker.
#### lenguajes de programación que se está utilzando
* **java:**  Java es el lenguaje de programación que se encarga de publicar los diferentes eventos 
* **javascript:** Se encarga de capturar los eventos que se lanzan y los almacena en una base de datos como un log
* **c#:** Se encarga de la captura de los eventos que lanza java y crear un usuario con los datos que recibe 

---
## crear empleado
Cuando se crea un empleado lo que hace es guardarlo primero en la base de datos y después en ese mismo momento enviar el mensaje a rabbitMq.

---
###  evento crear empleado
Formato del archivo json cuando se crea un nuevo empleado:

```json
{
  "id":"{id employee}",
  "nameUser":"{name employee}",
  "email":"{email employee}",
  "departmentID":"{departmentID}",
  "dateEnter":"{date register employee}"
}
```
### Evento eliminar empleado

---

```json

{
  "id_employee":"{id employee}",
  "name_employee":"{name employee}",
  "email":"{email employee}",
}
```


# English

### Microservice 1: Employee Management
This service handles the administration of employee records:
* **Persistence:** Uses a MySQL relational database.
* **Business Logic:** When creating a new employee, it is mandatory to assign them to an existing department.
* **Interaction:** The system performs a synchronous HTTP request to the Department Microservice to validate its existence.
* **Validation:** If the department does not exist, the operation is canceled, returning a null value to the client to ensure referential integrity.

**Requirements:** MySQL.

## 📌 Why was RabbitMQ chosen?

RabbitMQ was primarily chosen due to its extensive documentation, ease of use, and because it was not too difficult for the team to implement across the different programming languages used in the project.

---

## 📌 Programming languages used

- **Java**: Responsible for publishing the different events.  
- **JavaScript**: Handles capturing the emitted events and storing them in a database as logs.  
- **C#**: Responsible for consuming events published by Java and creating users based on the received data.  

---


### Event create employee

format of file json
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
