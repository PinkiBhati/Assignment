package com.jpa2.Employee2.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class CreditCard extends Payment {

    private int creditCardNo;

    public CreditCard() {

    }

    public CreditCard(int id, int amount, int creditCardNo) {
        super(id, amount);
        this.creditCardNo = creditCardNo;
    }

    public int getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(int creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardNo=" + creditCardNo +
                '}';
    }
}
