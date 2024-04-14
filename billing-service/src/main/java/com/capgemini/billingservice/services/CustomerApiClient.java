package com.capgemini.billingservice.services;

import com.capgemini.billingservice.models.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.capgemini.billingservice.constant.Constant.CUSTOMER_API;

@FeignClient(name = "customer-service", url = CUSTOMER_API)
public interface CustomerApiClient {

  //@GetMapping(value = "/{id}")
  @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
  CustomerResponse getCustomer(@PathVariable("id") Long id);
}
