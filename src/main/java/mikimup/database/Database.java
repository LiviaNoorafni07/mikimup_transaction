/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Database {

    static final String url = "jdbc:mysql://localhost:3306/mikimup";
    static final String user = "root";
    static final String pass = "Noorafni07#"; // SUMPAH INI AKU AMBIL DARI MODULE 4 LAPRAK BUKAN AI
    static Connection conn;
    public static Statement stmt;
    public static ResultSet rs;

    public void connect() {
        try {
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public void query(String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Kesalahan Query: " + ex.getMessage());
        }
    }

    public ResultSet view(String sql) {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Kesalahan View: " + ex.getMessage());
        }
        return rs;
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Kesalahan saat menutup koneksi: "
                    + ex.getMessage());
        }
    }

}
