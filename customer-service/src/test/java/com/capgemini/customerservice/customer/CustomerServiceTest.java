package com.capgemini.customerservice.customer;

import static com.capgemini.customerservice.customer.CustomerExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.capgemini.customerservice.exception.ResourceNotFoundException;
import com.capgemini.customerservice.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCustomer(){
        // Given
        Customer customer = Customer.builder()
                .email("email@example.com")
                .fullName("Johan Jon")
                .build();
        CustomerDto customerSaveDto = CustomerDto.builder()
                .email("email@example.com")
                .fullName("Johan Jon").build();

        mockStatic(CustomerValidationHelper.class);
        when(CustomerValidationHelper.isValidEmail(customer.getEmail())).thenReturn(true);
        when(customerRepository.save(ArgumentMatchers.any())).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerSaveDto);
        // When
        CustomerDto customerCreated = customerService.createCustomer(customerMapper.toDto(customer));
        // Then
        assertThat(customerCreated.getFullName()).isSameAs(customer.getFullName());
    }

    @Test
    void createCustomerShouldThrowsInValidEmailException(){
        // Given
        CustomerDto customerDto = CustomerDto.builder()
                .email("existing-example@.com")
                .fullName("Johan Jon").build();

        mockStatic(CustomerValidationHelper.class);
        when(CustomerValidationHelper.isValidEmail(customerDto.getEmail())).thenReturn(false);

        // Then
        assertThatThrownBy(() -> customerService.createCustomer(customerDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining(CUSTOMER_EMAIL_NOT_VALID_EXCEPTION.getValue()+customerDto.getEmail());
    }

    @Test
    void createCustomerShouldThrowsExceptionAlreadyExistEmail(){
        // Given
        CustomerDto customerDto = CustomerDto.builder()
                .email("existing@example.com")
                .fullName("Johan Jon").build();

        when(customerRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new Customer()));

        // Then
        assertThatThrownBy(() -> customerService.createCustomer(customerDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(CUSTOMER_ALREADY_EXIST_EXCEPTION.getValue()+customerDto.getEmail());
    }

    @Test
    void shouldReturnCustomerById(){
        // Given
        Long id = 1L;

        Customer customer = new Customer();
        customer.setId(id);
        CustomerDto expectedCustomerDto = new CustomerDto();
        expectedCustomerDto.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(expectedCustomerDto);

        // When
        CustomerDto actualCustomerDto = customerService.getCustomer(id);

        // Then
        assertThat(expectedCustomerDto.getFullName()).isSameAs(actualCustomerDto.getFullName());

    }

    @Test
    void createCustomerShouldThrowsNotFoundException(){
        // Given
        Long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> customerService.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(CUSTOMER_NOT_FOUND_EXCEPTION.getValue());

    }

    @Test
    void shouldReturnListOfCustomers(){
        // Given
        Customer customer = new Customer();
        customer.setId(1L);

        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDto> actualCustomers = customerService.getCustomers();

        // Then
        assertThat(actualCustomers).isNotEmpty();
    }

}