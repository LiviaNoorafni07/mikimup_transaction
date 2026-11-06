/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.model.Transaction;
import mikimup.model.TransactionDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mikimup.database.Database;
import mikimup.model.Product;

/**
 *
 * @author USER
 */
public class TransaksiController {

    private Database db;

    public TransaksiController() {
        this.db = new Database();
    }

    // Menampilkan tabel product (siREAD)
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM transaction ORDER BY id_transaction DESC");

        try {
            if (rs != null) {
                while (rs.next()) {
                    Transaction t = new Transaction(
                            rs.getInt("id_transaction"),
                            rs.getTimestamp("tanggal"),
                            rs.getDouble("total_harga"),
                            rs.getString("status")
                    );
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("Yah ada error dalam ambil Data: " + e.getMessage());
        } finally {
            db.disconnect();
        }

        return list;
    }
}
