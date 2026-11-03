/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup;

import mikimup.database.Database;
import mikimup.controller.ProductController;
import mikimup.model.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {

        ProductController controller = new ProductController();
        Database db = new Database();
        System.out.println("Coba Koneksi");

        db.connect();

        if (Database.stmt != null) {
            System.out.println("Sip bisa");
        } else {
            System.out.println("yahaha gagal");
        }

        System.out.println("tes show read");
        showDataMikimup(controller);

        db.disconnect();

        System.out.println("matiin koneksi");
    }

    public static void showDataMikimup(ProductController controller) {
        List<Product> daftarlist = controller.getAllProducts();
        for (Product pro : daftarlist) {
            System.out.println("Pro_" + pro.getIdProduct()
                    + "| Nama Produk : " + pro.getNamaProduct()
                    + "| Harga Produk : " + pro.getHarga()
                    + "| Kategori : " + pro.getKategori()
            );
        }
    }
}
