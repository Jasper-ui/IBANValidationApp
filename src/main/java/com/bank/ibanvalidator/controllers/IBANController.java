package com.bank.ibanvalidator.controllers;

import com.bank.ibanvalidator.controllers.requests.IBANFilter;
import com.bank.ibanvalidator.controllers.requests.IBANValidationRequest;
import com.bank.ibanvalidator.controllers.responses.IBANValidationResponse;
import com.bank.ibanvalidator.entites.IBAN;
import com.bank.ibanvalidator.interfaces.IBANService;
import com.bank.ibanvalidator.interfaces.IBANValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
public class IBANController {
    private final IBANValidationService ibanValidationService;
    private final IBANService ibanService;

    @Autowired
    public IBANController(IBANValidationService ibanValidationService, IBANService ibanService) {
        this.ibanValidationService = ibanValidationService;
        this.ibanService = ibanService;
    }

    @PostMapping("/validate")
    @ResponseBody
    public IBANValidationResponse validateIBAN(@Valid @RequestBody IBANValidationRequest ibanValidationRequest) {
        log.debug("IBAN Validations request with id received. Calling IBAN Validation service");
        return ibanValidationService.validateIBAN(ibanValidationRequest);
    }

    @GetMapping("/numbers")
    @ResponseBody
    public List<IBAN> getIBANHistory(@RequestParam IBANFilter filter) {
        log.debug("IBAN Validations request with id received. Calling IBAN Retrieval service");
        return ibanService.retrieveIBANs(filter);
    }
}
