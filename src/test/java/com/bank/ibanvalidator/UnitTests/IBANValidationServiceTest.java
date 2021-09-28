package com.bank.ibanvalidator.UnitTests;

import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.IBANValidationResponse;
import com.bank.ibanvalidator.entites.IBAN;
import com.bank.ibanvalidator.interfaces.IBANValidationService;
import com.bank.ibanvalidator.services.IBANRepositoryServiceImpl;
import com.bank.ibanvalidator.services.IBANValidationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class IBANValidationServiceTest {

  private IBANRepositoryServiceImpl repositoryService;

  private IBANValidationService service;

  @BeforeEach
  public void setup() {
    repositoryService = Mockito.mock(IBANRepositoryServiceImpl.class);

    service = new IBANValidationServiceImpl(repositoryService);
  }

  @Test
  public void validateIBANsTest() {
    Mockito.doNothing().when(repositoryService).createIBAN(any(IBAN.class));

    List<String> ibanToTest =
        List.of(
            "Random",
            "!£%^&*",
            "AT483200000012345864",
            "BH02CITI00001077181611",
            "LV97HABA0012345678910",
            "LV97HABA0012345678910!",
            "LA97HABA0012345678910",
            "LV97HA8A0012345678910",
            "LV97HABA00123456789100",
            "LV80HABA0012345542100",
            "LV80HABA 0012345542100",
            "LV80HABA0012345 542100",
            "LV80HABA0012345542100!",
            "lv80habs0012345542100");

    List<Boolean> expectedResults =
        List.of(
            false, false, false, false, true, false, false, false, false, true, true, true, false,
            true);

    List<IBAN> expectedIBANList = new ArrayList<>();

    for (int i = 0; i < ibanToTest.size(); i++) {
      expectedIBANList.add(
          new IBAN(
              ibanToTest.get(i).replaceAll("\\s+", "").toUpperCase(Locale.ROOT),
              expectedResults.get(i)));
    }

    IBANValidationResponse expectedResponse =
        new IBANValidationResponse(
            expectedResults.size(),
            (int) expectedResults.stream().filter(x -> x).count(),
            expectedIBANList);

    IBANValidationRequest request = new IBANValidationRequest(ibanToTest);

    IBANValidationResponse receivedResponse = service.validateIBAN(request);

    Assertions.assertEquals(
        expectedResponse.getNumberOfIBANs(), receivedResponse.getNumberOfIBANs());
    Assertions.assertEquals(
        expectedResponse.getNumberOfValidIBANs(), receivedResponse.getNumberOfValidIBANs());

    Mockito.verify(repositoryService, Mockito.atLeast(expectedResults.size())).createIBAN(any());

    List<IBAN> recievedIBANList = receivedResponse.getListOfIBANs();

    for (int i = 0; i < ibanToTest.size(); i++) {
      Assertions.assertEquals(
          expectedIBANList.get(i).getIbanNumber(), recievedIBANList.get(i).getIbanNumber());
      Assertions.assertEquals(expectedIBANList.get(i).isValid(), recievedIBANList.get(i).isValid());
    }
  }
}
