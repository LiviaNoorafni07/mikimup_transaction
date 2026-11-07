/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.model;

/**
 *
 * @author USER
 */
public class QrisPayment implements PaymentStrategy {

    private String pesan;

    @Override
    public boolean proses(double total, double bayar) {
        pesan = "Pembayaran QRIS Berhasil Diverifikasi (Ref: QRIS-OK)";
        return true;
    }

    @Override
    public String getPesan() {
        return pesan;
    }

}
