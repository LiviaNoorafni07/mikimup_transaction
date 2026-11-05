package mikimup.model;

/**
 *
 * @author USER
 */
public class Stok {

    private int idStok;
    private int idProduct;
    private int jumlah;
    private int minimum;
    private String status;

    //Construct Product READ
    public Stok(int idStok, int idProduct, int jumlah, int minimum, String status) {
        this.idStok = idStok;
        this.idProduct = idProduct;
        this.jumlah = jumlah;
        this.minimum = minimum;
        this.status = status;
    }

    //Get dan setter
    public int getIdStok() {
        return idStok;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getMinimum() {
        return minimum;
    }

    public String getStatus() {
        return status;
    }
}
