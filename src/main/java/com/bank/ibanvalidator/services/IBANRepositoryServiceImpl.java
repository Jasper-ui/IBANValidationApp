package com.bank.ibanvalidator.services;

import com.bank.ibanvalidator.entites.IBAN;
import com.bank.ibanvalidator.repository.IBANRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IBANRepositoryServiceImpl {
  private final IBANRepository ibanRepository;

  public IBANRepositoryServiceImpl(IBANRepository ibanRepository) {
    this.ibanRepository = ibanRepository;
  }

  @Transactional
  public void createIBAN(IBAN iban) {
    log.debug("Creating IBAN {} in repository", iban.getIbanNumber());

    try {
      if (!ibanRepository.existsByIbanNumber(iban.getIbanNumber())) {
        iban.setId(null == ibanRepository.findMaxId() ? 0 : ibanRepository.findMaxId() + 1);
        ibanRepository.save(iban);
      } else {
        log.warn("IBAN {} already exists in database", iban.getIbanNumber());
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public HashMap<String, Boolean> readAllIBAN() {
    log.debug("Returning all IBANs");

    List<IBAN> ibanList = ibanRepository.findAll();

    return generateHashMapFromIban(ibanList);
  }

  public HashMap<String, Boolean> readAllValidIBAN() {
    log.debug("Returning all valid IBANs");

    List<IBAN> ibanList = ibanRepository.findByValid(true);

    return generateHashMapFromIban(ibanList);
  }

  public HashMap<String, Boolean> readAllInvalidIBAN() {
    log.debug("Returning all invalid IBANs");

    List<IBAN> ibanList = ibanRepository.findByValid(false);

    return generateHashMapFromIban(ibanList);
  }

  private HashMap<String, Boolean> generateHashMapFromIban(List<IBAN> ibanList) {

    HashMap<String, Boolean> map = new HashMap<>();

    for (IBAN iban : ibanList) {
      map.put(iban.getIbanNumber(), iban.isValid());
    }

    return map;
  }

  // Unused update method
  @Transactional
  public void updateIBAN(IBAN iban) {
    log.debug("Calling update IBAN {} in repository action", iban.getIbanNumber());

    if (ibanRepository.existsByIbanNumber(iban.getIbanNumber())) {
      try {

        List<IBAN> ibans = ibanRepository.findByIbanNumber(iban.getIbanNumber());
        ibans.forEach(
            x -> {
              IBAN ibanToBeUpdate = ibanRepository.findById(x.getId()).orElse(null);
              Assert.notNull(ibanToBeUpdate, "IBAN " + iban.getIbanNumber() + " does not exist");
              ibanToBeUpdate.setIbanNumber(iban.getIbanNumber());
              ibanToBeUpdate.setValid(iban.isValid());
              ibanRepository.save(ibanToBeUpdate);
            });
        log.info("IBAN record updated to IBAN {}", iban.getIbanNumber());
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    } else {
      log.warn("IBAN {} does not exist in database", iban.getIbanNumber());
    }
  }

  // Unused delete method
  @Transactional
  public void deleteIBAN(IBAN iban) {
    log.debug("Calling delete IBAN {} in repository action", iban.getIbanNumber());

    if (ibanRepository.existsByIbanNumber(iban.getIbanNumber())) {
      try {
        List<IBAN> students = ibanRepository.findByIbanNumber(iban.getIbanNumber());
        ibanRepository.deleteAll(students);
        log.info("IBAN {} deleted successfully", iban.getIbanNumber());
      } catch (Exception e) {
        log.error(e.getMessage());
      }

    } else {
      log.warn("IBAN {} does not exist in database", iban.getIbanNumber());
    }
  }
}
