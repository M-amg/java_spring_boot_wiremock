package com.capgemini.customerservice.customer;

import com.capgemini.customerservice.exception.ResourceNotFoundException;
import com.capgemini.customerservice.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.capgemini.customerservice.customer.CustomerExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto){
        log.info("customer {}",customerDto.getEmail());
        if (!CustomerValidationHelper.isValidEmail(customerDto.getEmail())){
            throw new ValidationException(CUSTOMER_EMAIL_NOT_VALID_EXCEPTION.getValue()+ customerDto.getEmail());
        }
        Optional<Customer> savedEmployee = customerRepository.findByEmail(customerDto.getEmail());
        if(savedEmployee.isPresent()){
            throw new ResourceNotFoundException(CUSTOMER_ALREADY_EXIST_EXCEPTION.getValue()+ customerDto.getEmail());
        }
        Customer customer = customerRepository.save(customerMapper.toEntity(customerDto));
        log.info("saved customer  {}",customer.getEmail());

        return customerMapper.toDto(customer);
    }

    CustomerDto getCustomer(Long id){
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND_EXCEPTION.getValue()));
        return customerMapper.toDto(customer);
    }

    List<CustomerDto> getCustomers(){
        return customerRepository.findAll().stream().map(customerMapper::toDto).toList();
    }
}
