package com.employee_microservice;

import com.employee_microservice.service.ServiceEmployee;
import com.employee_microservice.service.ServiceRabbitMq;
import com.employee_microservice.api.MicroserviceDepartment;
import com.employee_microservice.exception.DeleteEmployeeExceptions;
import com.employee_microservice.model.dto.EmployeeDtoRequest;
import com.employee_microservice.model.dto.EmployeeDtoResponse;
import com.employee_microservice.model.entitys.Employee;
import com.employee_microservice.repository.EmployeeRepository;
import com.employee_microservice.util.MapperEmployee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceEmployeeTest {

    @InjectMocks
    private ServiceEmployee serviceEmployee;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MicroserviceDepartment apiMicroservice;

    @Mock
    private MapperEmployee mapperEmployee;

    @Mock
    private ServiceRabbitMq serviceRabbitMq;

    private Employee employee;
    private EmployeeDtoRequest dtoRequest;
    private EmployeeDtoResponse dtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setIdEmployee(1L);
        employee.setEmail("test@mail.com");
        employee.setNameOne("John");
        employee.setFirstSurname("Doe");

        dtoRequest = new EmployeeDtoRequest(
            1L,
            "John",
            "Michael",
            "Doe",
            "Smith",
            "3001234567",
            "Calle 123 #45-67",
            630001,
            "john.doe@mail.com",
            "Armenia",
            "Developer",
            10L);

            
        dtoResponse = new EmployeeDtoResponse(
            "John",
            "Michael",
            "Doe",
            "Smith",
            "3001234567",
            "Calle 123 #45-67",
            630001,
            "john.doe@mail.com",
            "Armenia",
            "Developer",
            10L
        );
    }

    @Test
    void testGetEmployeeForID() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(mapperEmployee.getEmployee(employee)).thenReturn(dtoResponse);

        EmployeeDtoResponse result = serviceEmployee.getEmployeeForID(1L);

        assertEquals("John", result.name_one());
        verify(employeeRepository).findById(1L);
    }

    @Test
    void testSaveEmployeeSuccess() throws IOException, InterruptedException {
        when(apiMicroservice.dataMicroserviceDepartment(anyString())).thenReturn(true);
        when(mapperEmployee.getDtoToEmployee(dtoRequest)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapperEmployee.getEmployee(employee)).thenReturn(dtoResponse);

        Optional<EmployeeDtoResponse> result = serviceEmployee.saveEmployee(dtoRequest);

        assertTrue(result.isPresent());
        assertEquals("john.doe@mail.com", result.get().email());
        verify(serviceRabbitMq).senMessageAndBrokerRabbitMq(any());
    }

    @Test
    void testSaveEmployeeFailsDepartment() throws IOException, InterruptedException {
        when(apiMicroservice.dataMicroserviceDepartment(anyString())).thenReturn(false);

        Optional<EmployeeDtoResponse> result = serviceEmployee.saveEmployee(dtoRequest);

        assertTrue(result.isEmpty());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DeleteEmployeeExceptions.class, () -> serviceEmployee.deleteEmployee(99L));
    }

    @Test
    void testDeleteEmployeeSuccess() throws DeleteEmployeeExceptions {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        serviceEmployee.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
        verify(serviceRabbitMq).sendMessageDeleteEmployee(any());
    }

}
