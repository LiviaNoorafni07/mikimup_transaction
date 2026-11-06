/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.database.Database;
import mikimup.model.Transaction;
import mikimup.model.TransactionDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class TransaksiDetailController {

    public List<TransactionDetail> getDetailByTransactionId(int idTransaction) {
        List<TransactionDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction_detail WHERE id_transaction = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaction);
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
