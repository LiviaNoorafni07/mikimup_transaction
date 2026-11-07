/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.repository;

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
public class TransactionRepositoryImpl implements TransactionRepository {

    private Database db;

    public TransactionRepositoryImpl() {
        this.db = new Database();
    }

    // Menampilkan tabel product (siREAD)
    public List<Transaction> findAll() {
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

    @Override
    public int saveMaster(Transaction transaction) {
        db.connect();
        Connection conn = db.getConnection();
        int generatedId = -1;

        if (conn == null) {
            System.out.println("Gagal terhubung ke database");
            return generatedId;
        }

        String sql = "INSERT INTO transaction (total_harga, status) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, transaction.getTotalharga());
            ps.setString(2, "selesai");
            ps.executeUpdate();

            ResultSet rsKey = ps.getGeneratedKeys();
            if (rsKey.next()) {
                generatedId = rsKey.getInt(1); // Ambil ID transaksi yang baru terbentuk
            }
        } catch (SQLException e) {
            System.out.println("Error saveMaster transaction: " + e.getMessage());
        } finally {
            db.disconnect();
        }

        return generatedId;
    }
}
