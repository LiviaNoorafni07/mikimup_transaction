package mikimup.model;

/**
 *
 * @author USER
 */
public class Product {

    private int idProduct;
    private String namaProduct;
    double harga;
    private String kategori;

    //Construct Product READ
    public Product(int idProduct, String namaProduct, double harga, String kategori) {
        this.idProduct = idProduct;
        this.namaProduct = namaProduct;
        this.harga = harga;
        this.kategori = kategori;
    }

    //Get dan setter
    public int getIdProduct() {
        return idProduct;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }
}
