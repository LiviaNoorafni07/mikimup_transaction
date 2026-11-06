/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.model;

/**
 *
 * @author USER
 */
public class TransactionDetail {

    private int idTransaction;
    private int idDetail;
    private int idProduct;
    private String namaProduct;
    private int jumlah;
    private double subtotal;

    //Construct Product READ
    public TransactionDetail(int idTransaction, int idDetail, int idProduct, String namaProduct, int jumlah, double subtotal) {
        this.idTransaction = idTransaction;
        this.idDetail = idDetail;
        this.idProduct = idProduct;
        this.namaProduct = namaProduct;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    //Get dan setter
    public int getIdTransaction() {
        return idTransaction;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public int getJumlah() {
        return jumlah;
    }
}
