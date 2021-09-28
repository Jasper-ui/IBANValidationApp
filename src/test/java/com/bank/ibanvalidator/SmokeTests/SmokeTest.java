package com.bank.ibanvalidator.SmokeTests;

import com.bank.ibanvalidator.controllers.IBANController;
import com.bank.ibanvalidator.interfaces.IBANService;
import com.bank.ibanvalidator.interfaces.IBANValidationService;
import com.bank.ibanvalidator.repository.IBANRepository;
import com.bank.ibanvalidator.services.IBANRepositoryServiceImpl;
import com.bank.ibanvalidator.services.IBANValidationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired private IBANController controller;
    @Autowired private IBANValidationService validationInterface;
    @Autowired private IBANValidationServiceImpl validationServiceImpl;
    @Autowired private IBANService retrievalService;
    @Autowired private IBANRepository repository;
    @Autowired private IBANRepositoryServiceImpl repositoryImpl;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(validationInterface).isNotNull();
        assertThat(validationServiceImpl).isNotNull();
        assertThat(retrievalService).isNotNull();
        assertThat(repository).isNotNull();
        assertThat(repositoryImpl).isNotNull();
    }
}
