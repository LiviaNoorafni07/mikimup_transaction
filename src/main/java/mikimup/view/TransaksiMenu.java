/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mikimup.view;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mikimup.model.PaymentStrategy;
import mikimup.model.QrisPayment;
import mikimup.model.CashPayment;
import mikimup.controller.ProductController;
import mikimup.controller.TransaksiController;
import mikimup.model.Product;
import mikimup.model.Transaction;
import mikimup.model.TransactionDetail;

/**
 *
 * @author USER
 */
public class TransaksiMenu extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TransaksiMenu.class.getName());
    private ProductController productController;
    private TransaksiController transaksiController;
    private DefaultTableModel modelSementara;
    private List<Product> listProduk;

    /**
     * Creates new form TransaksiMenu
     */
    public TransaksiMenu() {
        initComponents();
        txtId.setEditable(false);
        txtHarga.setEditable(false);
        txtTotalHarga.setEditable(false);
        txtKembalian.setEditable(false);

        productController = new ProductController();
        transaksiController = new TransaksiController();

        setupTabelSementara();
        loadComboProduk();

        initListeners();
    }

    private void setupTabelSementara() {
        modelSementara = new DefaultTableModel();
        modelSementara.addColumn("ID Produk");
        modelSementara.addColumn("Nama Produk");
        modelSementara.addColumn("Harga");
        modelSementara.addColumn("Jumlah");
        modelSementara.addColumn("Subtotal");
        tblTransaksiSementara.setModel(modelSementara);
    }

    private void loadComboProduk() {
        cbNamaProduk.removeAllItems();
        listProduk = productController.getAllProducts();
        if (listProduk != null && !listProduk.isEmpty()) {
            for (Product p : listProduk) {
                cbNamaProduk.addItem(p.getNamaProduct());
            }
            updateDetailProdukSelected();
        }
    }

    private void updateDetailProdukSelected() {
        int selectedIndex = cbNamaProduk.getSelectedIndex();
        if (selectedIndex != -1 && listProduk != null && selectedIndex < listProduk.size()) {
            Product p = listProduk.get(selectedIndex);
            txtId.setText(String.valueOf(p.getIdProduct()));
            txtHarga.setText(String.valueOf(p.getHarga()));
        }
    }

    private void initListeners() {
        cbNamaProduk.addActionListener(e -> updateDetailProdukSelected());
        tmbTambah.addActionListener(e -> tambahKeKeranjang());
        tmbEdit.addActionListener(e -> editKeranjang());
        tmbHapus.addActionListener(e -> hapusDariKeranjang());
        tmbTransaksi.addActionListener(e -> prosesTransaksiAkhir());

        tblTransaksiSementara.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihBarisKeranjang();
            }
        });

        txtHarga1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungKembalian();
            }
        });
    }

    private void tambahKeKeranjang() {
        if (txtId.getText().isEmpty() || txtJumlah.getText().trim().isEmpty()) {
            return;
        }
        try {
            int idProduct = Integer.parseInt(txtId.getText());
            String nama = cbNamaProduk.getSelectedItem().toString();
            double harga = Double.parseDouble(txtHarga.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            double subtotal = harga * jumlah;

            modelSementara.addRow(new Object[]{idProduct, nama, harga, jumlah, subtotal});
            txtJumlah.setText("");
            hitungTotalSemuaHarga();
        } catch (Exception e) {
        }
    }

    private void editKeranjang() {
        int row = tblTransaksiSementara.getSelectedRow();
        if (row == -1 || txtId.getText().isEmpty() || txtJumlah.getText().trim().isEmpty()) {
            return;
        }
        try {
            int idProduct = Integer.parseInt(txtId.getText());
            String nama = cbNamaProduk.getSelectedItem().toString();
            double harga = Double.parseDouble(txtHarga.getText());
            int jumlahBaru = Integer.parseInt(txtJumlah.getText().trim());
            if (jumlahBaru <= 0) {
                return;
            }
            modelSementara.setValueAt(idProduct, row, 0);
            modelSementara.setValueAt(nama, row, 1);
            modelSementara.setValueAt(harga, row, 2);
            modelSementara.setValueAt(jumlahBaru, row, 3);
            modelSementara.setValueAt(harga * jumlahBaru, row, 4);
            txtJumlah.setText("");
            hitungTotalSemuaHarga();

        } catch (NumberFormatException e) {
        }
    }

    private void hapusDariKeranjang() {
        int row = tblTransaksiSementara.getSelectedRow();
        if (row != -1) {
            modelSementara.removeRow(row);
            hitungTotalSemuaHarga();
        }
    }

    private void pilihBarisKeranjang() {
        int row = tblTransaksiSementara.getSelectedRow();
        if (row != -1) {
            txtId.setText(modelSementara.getValueAt(row, 0).toString());
            cbNamaProduk.setSelectedItem(modelSementara.getValueAt(row, 1).toString());
            txtHarga.setText(modelSementara.getValueAt(row, 2).toString());
            txtJumlah.setText(modelSementara.getValueAt(row, 3).toString());
        }
    }

    private void hitungTotalSemuaHarga() {
        double total = 0;
        for (int i = 0; i < modelSementara.getRowCount(); i++) {
            total += Double.parseDouble(modelSementara.getValueAt(i, 4).toString());
        }
        txtTotalHarga.setText(String.valueOf(total));
        hitungKembalian();
    }

    private void hitungKembalian() {
        try {
            double total = txtTotalHarga.getText().isEmpty() ? 0 : Double.parseDouble(txtTotalHarga.getText());
            double bayar = txtHarga1.getText().isEmpty() ? 0 : Double.parseDouble(txtHarga1.getText());
            txtKembalian.setText(String.valueOf(bayar - total));
        } catch (Exception e) {
            txtKembalian.setText("0.0");
        }
    }

    private void prosesTransaksiAkhir() {
        if (modelSementara.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Keranjang belanjaan masih kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] options = {"Tunai", "QRIS"};
        int pilihan = JOptionPane.showOptionDialog(this,
                "Pilih Metode Pembayaran:",
                "Metode Pembayaran",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (pilihan == -1) {
            return;
        }

        PaymentStrategy strategy;
        double jumlahBayar = 0;

        if (pilihan == 0) { // Tunai
            String inputBayar = txtHarga1.getText().trim();
            if (inputBayar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan nominal pembayaran tunai terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                jumlahBayar = Double.parseDouble(inputBayar);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nominal pembayaran harus berupa angka valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            strategy = new CashPayment();
        } else { // QRIS
            jumlahBayar = Double.parseDouble(txtTotalHarga.getText());
            strategy = new QrisPayment();
        }

        try {
            double total = Double.parseDouble(txtTotalHarga.getText());

            Transaction t = new Transaction();
            t.setTotalharga(total);
            t.setStatus("selesai");

            for (int i = 0; i < modelSementara.getRowCount(); i++) {
                int idProduct = Integer.parseInt(modelSementara.getValueAt(i, 0).toString());
                int jumlah = Integer.parseInt(modelSementara.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(modelSementara.getValueAt(i, 4).toString());
                t.addDetail(new TransactionDetail(idProduct, jumlah, subtotal));
            }

            String hasil = transaksiController.prosesTransaksi(t, jumlahBayar, strategy);

            if (hasil.startsWith("SUCCESS:")) {
                String pesanRespon = hasil.substring(8);

                JOptionPane.showMessageDialog(this,
                        "Transaksi Berhasil!\n" + pesanRespon,
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

                modelSementara.setRowCount(0);
                txtJumlah.setText("");
                txtTotalHarga.setText("");
                txtHarga1.setText("");
                txtKembalian.setText("");
            } else {
                JOptionPane.showMessageDialog(this, hasil, "Gagal Transaksi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan proses transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTransaksi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksiSementara = new javax.swing.JTable();
        lblNamaProduk = new javax.swing.JLabel();
        tmbTambah = new javax.swing.JButton();
        cbNamaProduk = new javax.swing.JComboBox<>();
        tmbEdit = new javax.swing.JButton();
        tmbHapus = new javax.swing.JButton();
        lblHarga = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        lblJumlah = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        lblTotalSemuaHarga = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();
        tmbTransaksi = new javax.swing.JButton();
        RiwayatTransakstmbi = new javax.swing.JButton();
        tmbkembali = new javax.swing.JButton();
        txtHarga1 = new javax.swing.JTextField();
        lblBayar = new javax.swing.JLabel();
        lblKembalian = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTransaksi.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        lblTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTransaksi.setText("Transaksi");

        tblTransaksiSementara.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTransaksiSementara);

        lblNamaProduk.setText("Nama Produk");

        tmbTambah.setText("Tambah Transaksi");

        cbNamaProduk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNamaProduk.addActionListener(this::cbNamaProdukActionPerformed);

        tmbEdit.setText("Edit");

        tmbHapus.setText("Hapus");

        lblHarga.setText("Harga");

        lblJumlah.setText("Jumlah");

        lblTotalSemuaHarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalSemuaHarga.setText("Total Semua Harga");

        tmbTransaksi.setText("Transaksi");

        RiwayatTransakstmbi.setText("Riwayat Transaksi");
        RiwayatTransakstmbi.addActionListener(this::RiwayatTransakstmbiActionPerformed);

        tmbkembali.setText("kembali");
        tmbkembali.addActionListener(this::tmbkembaliActionPerformed);

        lblBayar.setText("Bayar :");

        lblKembalian.setText("kembalian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtJumlah))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHarga))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tmbTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tmbEdit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tmbHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTotalSemuaHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblKembalian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTotalHarga)
                                            .addComponent(txtHarga1)
                                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(tmbTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tmbkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(124, 124, 124)
                                .addComponent(RiwayatTransakstmbi)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RiwayatTransakstmbi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tmbkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalSemuaHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHarga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tmbTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(tmbEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tmbHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tmbTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RiwayatTransakstmbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RiwayatTransakstmbiActionPerformed
        RiwayatTransaksi halHistoryPay = new RiwayatTransaksi();
        halHistoryPay.setLocationRelativeTo(null);
        halHistoryPay.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_RiwayatTransakstmbiActionPerformed

    private void tmbkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbkembaliActionPerformed
        MenuMikimup halUtama = new MenuMikimup();
        halUtama.setLocationRelativeTo(null);
        halUtama.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tmbkembaliActionPerformed

    private void cbNamaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNamaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNamaProdukActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TransaksiMenu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RiwayatTransakstmbi;
    private javax.swing.JComboBox<String> cbNamaProduk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBayar;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblKembalian;
    private javax.swing.JLabel lblNamaProduk;
    private javax.swing.JLabel lblTotalSemuaHarga;
    private javax.swing.JLabel lblTransaksi;
    private javax.swing.JTable tblTransaksiSementara;
    private javax.swing.JButton tmbEdit;
    private javax.swing.JButton tmbHapus;
    private javax.swing.JButton tmbTambah;
    private javax.swing.JButton tmbTransaksi;
    private javax.swing.JButton tmbkembali;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtHarga1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
