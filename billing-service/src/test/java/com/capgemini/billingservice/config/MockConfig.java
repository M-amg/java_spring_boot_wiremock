package com.capgemini.billingservice.config;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MockConfig {

  public static void mockCustomerResponse() throws IOException {
    stubFor(
        get("api/v1/customers/1")
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(
                        copyToString(
                            MockConfig.class
                                .getClassLoader()
                                .getResourceAsStream("mappings/customer.json"),
                            defaultCharset()))));
  }
}
