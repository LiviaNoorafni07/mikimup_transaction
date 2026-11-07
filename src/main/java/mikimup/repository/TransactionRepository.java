/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mikimup.repository;

import mikimup.model.Transaction;
import java.util.List;

/**
 *
 * @author USER
 */
public interface TransactionRepository {

    List<Transaction> findAll();

    int saveMaster(Transaction transaction);
}
