/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mikimup.model;

/**
 *
 * @author USER
 */
public interface PaymentStrategy {

    boolean proses(double total, double bayar);

    String getPesan();
}
