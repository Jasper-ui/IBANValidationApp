package com.bank.ibanvalidator.FullTest;

import com.bank.ibanvalidator.controllers.requests.IBANFilter;
import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.IBANValidationResponse;
import com.bank.ibanvalidator.entites.IBAN;
import com.bank.ibanvalidator.interfaces.IBANService;
import com.bank.ibanvalidator.repository.IBANRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:sqlite:test.db"})
public class FullTest {
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private IBANRepository repository;

  @Autowired private IBANService service;

  @Test
  public void fullTest() {
    List<String> input = List.of("Hello", "BH02CITI00001077181611", "lv80habs0012345542100");

    IBANValidationRequest request = new IBANValidationRequest(input);

    List<IBAN> listOfIbans =
        List.of(
            new IBAN(input.get(0).replaceAll("\\s+", "").toUpperCase(Locale.ROOT), false),
            new IBAN(input.get(1).replaceAll("\\s+", "").toUpperCase(Locale.ROOT), false),
            new IBAN(input.get(2).replaceAll("\\s+", "").toUpperCase(Locale.ROOT), true));

    IBANValidationResponse expectedResponse = new IBANValidationResponse(3, 1, listOfIbans);
    IBANValidationResponse actualResponse =
        submitPostRequest(request, IBANValidationResponse.class);

    Assertions.assertEquals(
        expectedResponse.getNumberOfValidIBANs(), actualResponse.getNumberOfValidIBANs());
    Assertions.assertEquals(expectedResponse.getNumberOfIBANs(), actualResponse.getNumberOfIBANs());

    HashMap<String, Boolean> recievedInvalid = submitGetRequest(IBANFilter.INVALID);
    HashMap<String, Boolean> recievedValid = submitGetRequest(IBANFilter.VALID);
    HashMap<String, Boolean> recievedAll = submitGetRequest(IBANFilter.ALL);

    Assertions.assertEquals(
        listOfIbans.get(2).isValid(), recievedValid.get(listOfIbans.get(2).getIbanNumber()));

    Assertions.assertEquals(
            listOfIbans.get(1).isValid(), recievedInvalid.get(listOfIbans.get(1).getIbanNumber()));

    Assertions.assertEquals(
            listOfIbans.get(0).isValid(), recievedInvalid.get(listOfIbans.get(0).getIbanNumber()));

    for (int i = 0; i < input.size(); i++) {
      Assertions.assertEquals(
              listOfIbans.get(i).isValid(), recievedAll.get(listOfIbans.get(i).getIbanNumber()));
      Assertions.assertEquals(
          expectedResponse.getListOfIBANs().get(i).getIbanNumber(),
          actualResponse.getListOfIBANs().get(i).getIbanNumber());
      Assertions.assertEquals(
          expectedResponse.getListOfIBANs().get(i).isValid(),
          actualResponse.getListOfIBANs().get(i).isValid());
      Assertions.assertTrue(
          repository.existsByIbanNumber(actualResponse.getListOfIBANs().get(i).getIbanNumber()));
      repository.delete(actualResponse.getListOfIBANs().get(i));
    }
  }

  public <T> T submitPostRequest(IBANValidationRequest requestTicket, Class<T> className) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<IBANValidationRequest> request = new HttpEntity<>(requestTicket, headers);

    return this.restTemplate
        .postForEntity("http://localhost:" + port + "/validate", request, className)
        .getBody();
  }

  public HashMap<String, Boolean> submitGetRequest(IBANFilter filter) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/numbers")
            .queryParam("filter", filter);

    HttpEntity<?> request = new HttpEntity<>(headers);

    return this.restTemplate
                    .exchange(builder.toUriString(), HttpMethod.GET, request, HashMap.class)
                    .getBody();
  }
        }


