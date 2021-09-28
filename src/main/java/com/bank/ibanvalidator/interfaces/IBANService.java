package com.bank.ibanvalidator.interfaces;

import com.bank.ibanvalidator.controllers.requests.IBANFilter;
import com.bank.ibanvalidator.entites.IBAN;

import java.util.List;

public interface IBANService {
    List<IBAN> retrieveIBANs(IBANFilter filter);
}
