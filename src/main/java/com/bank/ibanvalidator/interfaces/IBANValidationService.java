package com.bank.ibanvalidator.interfaces;

import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.IBANValidationResponse;

public interface IBANValidationService {
    IBANValidationResponse validateIBAN(IBANValidationRequest ibanValidationRequest);
}
