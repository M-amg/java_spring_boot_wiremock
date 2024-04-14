package com.capgemini.customerservice.customer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired CustomerRepository customerRepository;
  private Customer customer;

  @BeforeEach
  public void setup() {
    this.customer = Customer.builder().fullName("Johan Jow").email("example@example.com").build();
  }

  @Test
  void shouldBeSaveCustomer() {
    // When
    Customer saveCustomer = customerRepository.save(customer);
    // Then
    assertThat(saveCustomer).isNotNull();
    assertThat(saveCustomer.getId()).isPositive();
  }

  @Test
  void shouldReturnCustomerByEmail() {
    //Given
    Customer saveCustomer = customerRepository.save(customer);
    //When
    Optional<Customer> customerByEmail = customerRepository.findByEmail("example@example.com");
    // Then
    assertThat(customerByEmail).isPresent();
  }
  
}
