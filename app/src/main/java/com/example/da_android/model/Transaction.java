package com.example.da_android.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String idTrx;
    private String note;
    private String userName;
    private int money;
    private String type;
    private String idCtg;
    private String trxDate;


    public Transaction(String idTrx, String note, String userName, int money, String type, String idCtg, String trxDate) {
        this.idTrx = idTrx;
        this.note = note;
        this.userName = userName;
        this.money = money;
        this.type = type;
        this.idCtg = idCtg;
        this.trxDate = trxDate;
    }
    public Transaction(String type, String idCtg, int money, String trxDate) {
        this.money = money;
        this.type = type;
        this.idCtg = idCtg;
        this.trxDate = trxDate;
    }

    public Transaction() {
    }

    public String getIdTrx() {
        return idTrx;
    }

    public void setIdTrx(String idTrx) {
        this.idTrx = idTrx;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdCtg() {
        return idCtg;
    }

    public void setIdCtg(String idCtg) {
        this.idCtg = idCtg;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
