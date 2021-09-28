package com.bank.ibanvalidator.controllers.requests;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class IBANValidationRequest {
    @NotEmpty(message = "IBAN numbers list cannot be null or empty")
    private List<String> ibanNumbers;

    public IBANValidationRequest(List<String> ibanNumbers) {
        this.ibanNumbers = ibanNumbers;
    }

    public IBANValidationRequest() {
    }

    public List<String> getIbanNumbers() {
        return ibanNumbers;
    }

    public void setIbanNumbers(List<String> ibanNumbers) {
        this.ibanNumbers = ibanNumbers;
    }

    public int getNumberOfIBANs() {
        if(this.ibanNumbers == null)
        {
            return 0;
        }

        return ibanNumbers.size();
    }
}
