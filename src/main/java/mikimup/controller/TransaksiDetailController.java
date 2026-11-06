/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.database.Database;
import mikimup.model.Transaction;
import mikimup.model.TransactionDetail;
import mikimup.database.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class TransaksiDetailController {

    private Database db;

    public TransaksiDetailController() {
        this.db = new Database();
    }

    public List<TransactionDetail> getDetailByTransactionId(int idTransaction) {
        List<TransactionDetail> list = new ArrayList<>();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM transaction_detail WHERE id_transaction = " + idTransaction);
        try {
            if (rs != null) {
                while (rs.next()) {
                    TransactionDetail td = new TransactionDetail(
                            rs.getInt("id_detail"),
                            rs.getInt("id_transaction"),
                            rs.getInt("id_product"),
                            rs.getInt("jumlah"),
                            rs.getDouble("subtotal")
                    );
                    list.add(td);
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
