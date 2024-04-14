package com.capgemini.billingservice.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.capgemini.billingservice.config.MockConfig;
import com.capgemini.billingservice.models.CustomerResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@WireMockTest(httpPort = 9561)
class CustomerClientApiTest {

  @Autowired private CustomerApiClient customerApiClient;

  @Test
  void shouldResponseWithCustomer() throws IOException {
    MockConfig.mockCustomerResponse();
    long customerId = 1;
    CustomerResponse customerResponse = customerApiClient.getCustomer(customerId);
    assertThat(customerResponse.email()).isEqualTo("ahmed@gmail.com");
  }
}
