/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.repository;

import mikimup.database.Database;
import mikimup.model.TransactionDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class TransactionDetailRepositoryImpl implements TransactionDetailRepository {

    private final Database db;

    public TransactionDetailRepositoryImpl() {
        this.db = new Database();
    }

    @Override
    public List<TransactionDetail> findByTransactionId(int idTransaction) {
        List<TransactionDetail> list = new ArrayList<>();
        db.connect();
        String sql = "SELECT td.*, p.nama_product FROM transaction_detail td "
                + "JOIN product p ON td.id_product = p.id_product "
                + "WHERE td.id_transaction = " + idTransaction;
        ResultSet rs = db.view(sql);
        try {
            if (rs != null) {
                while (rs.next()) {
                    TransactionDetail td = new TransactionDetail(
                            rs.getInt("id_detail"),
                            rs.getInt("id_transaction"),
                            rs.getInt("id_product"),
                            rs.getString("nama_product"),
                            rs.getInt("jumlah"),
                            rs.getDouble("subtotal")
                    );
                    list.add(td);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findByTransactionId: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return list;
    }

    @Override
    public boolean saveDetail(int idTransaction, TransactionDetail detail) {
        db.connect();
        Connection conn = db.getConnection();
        String sql = "INSERT INTO transaction_detail (id_transaction, id_product, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idTransaction);
            ps.setInt(2, detail.getIdProduct());
            ps.setInt(3, detail.getJumlah());
            ps.setDouble(4, detail.getSubtotal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error saveDetail: " + e.getMessage());
            return false;
        } finally {
            db.disconnect();
        }
    }

}
