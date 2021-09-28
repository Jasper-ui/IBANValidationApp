package com.bank.ibanvalidator.repository;

import com.bank.ibanvalidator.entites.IBAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBANRepository extends JpaRepository<IBAN, Integer> {

    public boolean existsByIbanNumber(String iban);

    public List<IBAN> findByValid(boolean valid);

    public List<IBAN> findByIbanNumber(String iban);

    @Query("select max(iban.id) from IBAN iban")
    public Integer findMaxId();
}
