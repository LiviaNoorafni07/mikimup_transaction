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

    public boolean simpanTransaksi(Transaction transaction) {
        db.connect();
        Connection conn = db.getConnection();
        if (conn == null) {
            System.out.println("Yah gagal nyambung database");
            return false;
        }
        String sqlTransPay = "INSERT INTO transaction (total_harga, status) Values (?, ?)";
        String sqlTransDetail = "INSERT INTO transaction_detail (id_transaction, id_product, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE stok SET jumlah = ?, status = ? WHERE id_product = ?";

        try {
            PreparedStatement psTx = conn.prepareStatement(sqlTransPay, Statement.RETURN_GENERATED_KEYS);
            psTx.setDouble(1, transaction.getTotalharga());
            psTx.setString(2, "selesai");
            psTx.executeUpdate();

            // Ambil ID nota transaksi baru dibuat
            ResultSet rsKey = psTx.getGeneratedKeys();
            int idTx = -1;
            if (rsKey.next()) {
                idTx = rsKey.getInt(1);
            }
            for (TransactionDetail item : transaction.getDetails()) {
                // 1. Simpan ke transaction_detail
                PreparedStatement psDetail = conn.prepareStatement(sqlTransDetail);
                psDetail.setInt(1, idTx);
                psDetail.setInt(2, item.getIdProduct());
                psDetail.setInt(3, item.getJumlah());
                psDetail.setDouble(4, item.getSubtotal());
                psDetail.executeUpdate();

                // 2. Potong stok langsung di MySQL
                PreparedStatement psStok = conn.prepareStatement(sqlUpdateStok);
                psStok.setInt(1, item.getJumlah());
                psStok.setInt(2, item.getJumlah());
                psStok.setInt(3, item.getIdProduct());
                psStok.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error simpan transaksi: " + e.getMessage());
            return false;
        } finally {
            db.disconnect();
        }
    }
}
