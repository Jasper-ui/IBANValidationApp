package com.bank.ibanvalidator.controllers.responses;

import com.bank.ibanvalidator.entites.IBAN;

import java.util.List;

public class IBANValidationResponse {
    private int numberOfIBANs;
    private int numberOfValidIBANs;
    private List<IBAN> listOfIBANs;

    public IBANValidationResponse(int numberOfIBANs, int numberOfValidIBANs, List<IBAN> listOfIBANs) {
        this.numberOfIBANs = numberOfIBANs;
        this.numberOfValidIBANs = numberOfValidIBANs;
        this.listOfIBANs = listOfIBANs;
    }

    public int getNumberOfIBANs() {
        return numberOfIBANs;
    }

    public void setNumberOfIBANs(int numberOfIBANs) {
        this.numberOfIBANs = numberOfIBANs;
    }

    public int getNumberOfValidIBANs() {
        return numberOfValidIBANs;
    }

    public void setNumberOfValidIBANs(int numberOfValidIBANs) {
        this.numberOfValidIBANs = numberOfValidIBANs;
    }

    public List<IBAN> getListOfIBANs() {
        return listOfIBANs;
    }

    public void setListOfIBANs(List<IBAN> listOfIBANs) {
        this.listOfIBANs = listOfIBANs;
    }
}
