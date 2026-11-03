/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.database.Database;
import mikimup.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ProductController {

    private Database db;

    public ProductController() {
        this.db = new Database();
    }

    // Menampilkan tabel product (siREAD)
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        db.connect();
        ResultSet rs = db.view("SELECT * FROM product");

        try {
            if (rs != null) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getInt("id_product"),
                            rs.getString("nama_product"),
                            rs.getDouble("harga"),
                            rs.getString("kategori")
                    );
                    list.add(p);
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
