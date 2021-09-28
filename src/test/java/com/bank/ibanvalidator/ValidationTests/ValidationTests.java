package com.bank.ibanvalidator.ValidationTests;

import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.ValidationErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationTests {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void ibanNumberListIsNullRequest() {
    IBANValidationRequest request = new IBANValidationRequest();

    ValidationErrorResponse expectedResponse =
            new ValidationErrorResponse(400, "ibanNumbers", "IBAN numbers list cannot be null or empty");

    ValidationErrorResponse result = submitPostRequest(request, ValidationErrorResponse.class);

    assertValidationErrors(expectedResponse, result);
  }

  @Test
  public void ibanNumberListIsEmptyRequest() {
    IBANValidationRequest request = new IBANValidationRequest(List.of());

    ValidationErrorResponse expectedResponse =
        new ValidationErrorResponse(400, "ibanNumbers", "IBAN numbers list cannot be null or empty");

    ValidationErrorResponse result = submitPostRequest(request, ValidationErrorResponse.class);

    assertValidationErrors(expectedResponse, result);
  }

  public <T> T submitPostRequest(IBANValidationRequest requestTicket, Class<T> className) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<IBANValidationRequest> request = new HttpEntity<>(requestTicket, headers);

    return this.restTemplate
        .postForEntity("http://localhost:" + port + "/validate", request, className)
        .getBody();
  }

  public void assertValidationErrors(
      ValidationErrorResponse expected, ValidationErrorResponse received) {
    assert received != null;
    Assertions.assertEquals(expected.getResponseCode(), received.getResponseCode());
    Assertions.assertEquals(expected.getFieldName(), received.getFieldName());
    Assertions.assertEquals(expected.getErrorMessage(), received.getErrorMessage());
  }
    }