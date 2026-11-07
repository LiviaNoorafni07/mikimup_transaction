/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.database.Database;
import mikimup.model.Stok;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author USER
 */
public class StokController {

    private Database db;

    public StokController() {
        this.db = new Database();
    }

    // Menampilkan tabel product (siREAD)
    public List<Stok> getAllStok() {
        List<Stok> list = new ArrayList<>();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM stok");

        try {
            if (rs != null) {
                while (rs.next()) {
                    Stok s = new Stok(
                            rs.getInt("id_stok"),
                            rs.getInt("id_product"),
                            rs.getInt("jumlah"),
                            rs.getInt("minimum"),
                            rs.getString("status")
                    );
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Yah ada error dalam ambil Data: " + e.getMessage());
        } finally {
            db.disconnect();
        }

        return list;
    }

    public Stok getStokByProductId(int idProduct) {
        Stok stok = null;
        db.connect();
        ResultSet rs = db.view("SELECT * FROM stok WHERE id_product = " + idProduct);

        try {
            if (rs != null && rs.next()) {
                stok = new Stok(
                        rs.getInt("id_stok"),
                        rs.getInt("id_product"),
                        rs.getInt("jumlah"),
                        rs.getInt("minimum"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Yah ada error dalam ambil Data: " + e.getMessage());
        } finally {
            db.disconnect();
        }
        return stok;
    }

    public boolean kurangiStok(int idProduct, int jumlahBeli) {
        Stok s = getStokByProductId(idProduct);
        if (s == null || s.getJumlah() < jumlahBeli) {
            return false;
        }

        int sisaStok = s.getJumlah() - jumlahBeli;
        String statusBaru = "tersedia";
        if (sisaStok <= 0) {
            statusBaru = "habis";
        } else if (sisaStok <= s.getMinimum()) {
            statusBaru = "menipis";
        }

        db.connect();
        Connection conn = db.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE stok SET jumlah = ?, status = ? WHERE id_product = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, sisaStok);
                ps.setString(2, statusBaru);
                ps.setInt(3, idProduct);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error kurangiStok: " + e.getMessage());
            } finally {
                db.disconnect();
            }
            return true;
        }
        db.disconnect();
        return false;
    }
}
