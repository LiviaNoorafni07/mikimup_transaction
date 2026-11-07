/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mikimup.controller;

import mikimup.model.Transaction;
import mikimup.model.TransactionDetail;
import mikimup.repository.TransactionRepository;
import mikimup.repository.TransactionDetailRepository;
import mikimup.repository.TransactionRepositoryImpl;
import mikimup.repository.TransactionDetailRepositoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mikimup.database.Database;
import mikimup.model.Product;

/**
 *
 * @author USER
 */
public class TransaksiController {

    private Database db;
    private final TransactionRepository transactionRepo;
    private final TransactionDetailRepository detailRepo;
    private final StokController stokController;

    public TransaksiController() {
        this.transactionRepo = new TransactionRepositoryImpl();
        this.detailRepo = new TransactionDetailRepositoryImpl();
        this.stokController = new StokController();
    }

    public TransaksiController(TransactionRepository tRepo, TransactionDetailRepository dRepo, StokController sController) {
        this.transactionRepo = tRepo;
        this.detailRepo = dRepo;
        this.stokController = sController;
    }

    // Menampilkan tabel product (siREAD)
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<TransactionDetail> getDetailByTransactionId(int idTransaction) {
        return detailRepo.findByTransactionId(idTransaction);
    }

    public boolean simpanTransaksi(Transaction transaction) {
        int idTx = transactionRepo.saveMaster(transaction);
        if (idTx == -1) {
            return false;
        }

        for (TransactionDetail item : transaction.getDetails()) {
            boolean detailBerhasil = detailRepo.saveDetail(idTx, item);
            if (detailBerhasil) {
                stokController.kurangiStok(item.getIdProduct(), item.getJumlah());
            }
        }
        return true;
    }
}
