/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.model;

import java.util.Date;

/**
 *
 * @author USER
 */
public class Transaction {

    private int idTransaction;
    private Date tanggal;
    private double totalharga;
    private String status;

    //Construct Product READ
    public Transaction(int idTransaction, Date tanggal, double totalharga, int minimum, String status) {
        this.idTransaction = idTransaction;
        this.tanggal = tanggal;
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
}
