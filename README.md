

# Compresiòn

Es un proyecto que utiliza la arquitectura orientada a microservicios:

## Microservicio 1: gestiòn de empleados
 
Para este microservico lo que se hace es crear al empleado, tiene una base de datos SQL(relacional), que es mysql,cuando el microservicio se utiliza para crear a un nuevo empleado es necesario que se le asigne un  departamento, este departamento esta gestionado por otro mciroservico(màs adelante se daran màs detalles), lo que hace es que cuando se crea un nuevo empleado se debe de enviar una solcitud http al mucroservicio de departamnetos y verificar si existe, en caso de que no exista no se puede agregar lo que es el empleado y se envia un null al cliente, si todo sigue su flujo como deberia de ser, se crea el empelado correctamente. 

### Requisitos
- MYSQL
 

## Microservicio 2: Gestion de departamento

Para este microservicio se escogio la base de datos relacioal postgresql debido a su simplicidad, tambièn se busca debido a que las entidias pueden crecer y nos ayudan mucho al manejo de la base de datos, lo que se busca es poder agregar departamnetos, buscarlos. 

### Requisitos
- postgresql

# Configuraciòn

Se debe de tener conocimiento de las variables de entorno que se debe utilizar  en el docker compose, debido a que se utiliza para poder hacer que todos los servicios de desplegaran al mismo tiempo y sea mucho màs fàcil el poder gestionaralo, tambièn hay que tener en cuenta que la conexiòn entre empleados y departamentos funciona de forma sincronica, esto es debido  a que necesitamos bloquear un servicio para esperar la respuesta del otro y ahì si poder devolver una respuesta al cliente





