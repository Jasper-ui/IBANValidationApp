package com.bank.ibanvalidator.interfaces;

import com.bank.ibanvalidator.controllers.requests.IBANFilter;

import java.util.HashMap;

public interface IBANService {
    HashMap<String,Boolean> retrieveIBANs(IBANFilter filter);
}
