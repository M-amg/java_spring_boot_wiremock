package com.capgemini.customerservice.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/customers";
    }

    @Test
    @Sql(statements = "DELETE FROM customer WHERE 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCustomerList() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("ahmed");
        customer.setEmail("ahmed@example.com");
        customerRepository.save(customer);

        // When
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl(), List.class);
        List<Customer> customerList = response.getBody();

        // Then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(customerList)
                .as("Customer list should not be null")
                .isNotNull()
                .as("Customer list should not be empty")
                .hasSize(1)
        ;
    }

    @Test
    @Sql(statements = "DELETE FROM customer WHERE 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateCustomer() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName("ahmed Doe");
        customerDto.setEmail("ahmed@example.com");

        // When
        ResponseEntity<CustomerDto> response = restTemplate.postForEntity(getBaseUrl(), customerDto, CustomerDto.class);
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(customerRepository.findByEmail("ahmed@example.com")).isPresent();
    }

    @Test
    @Sql(statements = "DELETE FROM customer WHERE 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetCustomerById() {
        // Given
        long customerId = 1L; 
        Customer customer = new Customer();
        customer.setFullName("ahmed");
        customer.setEmail("ahmed@example.com");
        customerRepository.save(customer);

        // When
        ResponseEntity<CustomerDto> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + customerId, CustomerDto.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFoundException() {
        // Given
        long customerId = 1L;

        // When
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + customerId, ErrorDto.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo(CustomerExceptionMessage.CUSTOMER_NOT_FOUND_EXCEPTION.getValue());
    }


    @Test
    void testCreateCustomerByIdValidationException() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName("ahmed Doe");
        customerDto.setEmail("ahmed.example.com");

        // When
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(getBaseUrl(), customerDto, ErrorDto.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo(CustomerExceptionMessage.CUSTOMER_EMAIL_NOT_VALID_EXCEPTION.getValue()+"ahmed.example.com");
    }
}
