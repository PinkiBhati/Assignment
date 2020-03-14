package com.jpa2.Employee2.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class Cheque extends Payment {

    private int chequeNo;

    public Cheque() {
    }

    public Cheque(int id, int amount, int chequeNo) {
        super(id, amount);
        this.chequeNo = chequeNo;
    }

    public int getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(int chequeNo) {
        this.chequeNo = chequeNo;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "chequeNo=" + chequeNo +
                '}';
    }
}
