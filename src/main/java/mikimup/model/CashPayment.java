/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.model;

/**
 *
 * @author USER
 */
public class CashPayment implements PaymentStrategy {

    private String pesan;

    @Override
    public boolean proses(double total, double bayar) {
        if (bayar < total) {
            pesan = "Uang pembayaran tunai kurang!";
            return false;
        }
        double kembalian = bayar - total;
        pesan = "Kembalian: Rp " + String.format("%.0f", kembalian);
        return true;
    }

    @Override
    public String getPesan() {
        return pesan;
    }
}
