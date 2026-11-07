/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class Transaction {

    private int idTransaction;
    private Date tanggal;
    private double totalharga;
    private String status;
    private List<TransactionDetail> details = new ArrayList();

    public Transaction() {
    }

    //Construct Product READ
    public Transaction(int idTransaction, Date tanggal, double totalharga, String status) {
        this.idTransaction = idTransaction;
        this.tanggal = tanggal;
        this.totalharga = totalharga;
        this.status = status;
    }

    public Transaction(double totalharga, String status) {
        this.totalharga = totalharga;
        this.status = status;
    }

    //Get dan setter
    public int getIdTransaction() {
        return idTransaction;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public double getTotalharga() {
        return totalharga;
    }

    public String getStatus() {
        return status;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void getStatus(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }

    public void addDetail(TransactionDetail detail) {
        this.details.add(detail);
    }

    public void setTotalharga(double totalharga) {
        this.totalharga = totalharga;
    }
}
