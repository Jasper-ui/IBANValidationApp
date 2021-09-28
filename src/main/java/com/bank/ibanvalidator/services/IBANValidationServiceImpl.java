package com.bank.ibanvalidator.services;

import com.bank.ibanvalidator.controllers.requests.IBANFilter;
import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.IBANValidationResponse;
import com.bank.ibanvalidator.entites.IBAN;
import com.bank.ibanvalidator.interfaces.IBANService;
import com.bank.ibanvalidator.interfaces.IBANValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class IBANValidationServiceImpl implements IBANValidationService, IBANService {

  private static final String BASIC_REGEX = "[LV]{2}[0-9]{2}[A-Za-z]{4}[0-9]{13}";

  private final IBANRepositoryServiceImpl repository;

  @Autowired
  public IBANValidationServiceImpl(IBANRepositoryServiceImpl repository) {
    this.repository = repository;
  }

  @Override
  public IBANValidationResponse validateIBAN(IBANValidationRequest ibanValidationRequest) {

    Assert.notNull(ibanValidationRequest, "Submitted iban validation request is null");
    Assert.notNull(
        ibanValidationRequest.getIbanNumbers(), "Submitted iban validation number list is null");
    Assert.notEmpty(
        ibanValidationRequest.getIbanNumbers(), "Submitted iban validation number list is empty");

    List<String> numbersToBeValidated = ibanValidationRequest.getIbanNumbers();

    List<IBAN> ibans = new java.util.ArrayList<>();

    for (String numberToBeValidated : numbersToBeValidated) {
      String number = numberToBeValidated.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);

      boolean valid = validateNumber(number);

      IBAN newIban = createIBAN(number, valid);

      ibans.add(newIban);

      repository.createIBAN(newIban);
    }

    int validIbanCount = (int) ibans.stream().filter(IBAN::isValid).count();

    return new IBANValidationResponse(
        ibanValidationRequest.getNumberOfIBANs(), validIbanCount, ibans);
  }

  @Override
  public List<IBAN> retrieveIBANs(IBANFilter filter) {
    if (filter == IBANFilter.VALID) {
      return repository.readAllValidIBAN();
    } else if (filter == IBANFilter.INVALID) {
      return repository.readAllInvalidIBAN();
    }

    return repository.readAllIBAN();
  }

  /*
   * Ideally, this software should account for IBANs from different countries/have separate rules implemented for each
   * For simplicity , I will consider only LV IBANs to be valid and have the validations rules for LV only
   * According to internet sources LV IBAN is exactly 21 characters long
   * First two letters are the Country code LV and the subsequent 2 are the control numbers
   * The next 4 letters are the Bank identification letters
   * and the final 13 are the clients account number
   * Input numberToBeValidated has all whitespaces removed and is uppercase
   */
  private boolean validateNumber(String numberToBeValidated) {

    Pattern pattern = Pattern.compile(BASIC_REGEX);

    Matcher matcher = pattern.matcher(numberToBeValidated);

    log.debug("IBAN {} is valid: {}" , numberToBeValidated, matcher.matches());

    return matcher.matches();
  }

  private IBAN createIBAN(String numberToBeValidated, boolean valid) {
    return new IBAN(numberToBeValidated, valid);
  }
}
