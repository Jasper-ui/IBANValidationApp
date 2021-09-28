package com.bank.ibanvalidator.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IBAN {
    @Id
    private int id;
    private String ibanNumber;
    private boolean valid;

    public IBAN(String ibanNumber, boolean valid) {
        this.ibanNumber = ibanNumber;
        this.valid = valid;
    }

    public IBAN() {

    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "id=" + id +
                ", ibanNumber='" + ibanNumber + '\'' +
                ", valid=" + valid +
                '}';
    }
}
