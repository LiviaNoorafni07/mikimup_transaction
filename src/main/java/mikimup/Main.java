/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup;

import mikimup.database.Database;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {

        Database db = new Database();
        System.out.println("Coba Koneksi");

        db.connect();

        if (Database.stmt != null) {
            System.out.println("Sip bisa");
        } else {
            System.out.println("yahaha gagal");
        }

        System.out.println("test lainnya");

        db.disconnect();

        System.out.println("matiin koneksi");
    }

}
