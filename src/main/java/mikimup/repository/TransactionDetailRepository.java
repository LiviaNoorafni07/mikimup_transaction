/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mikimup.repository;

import mikimup.model.TransactionDetail;
import java.util.List;

/**
 *
 * @author USER
 */
public interface TransactionDetailRepository {

    List<TransactionDetail> findByTransactionId(int idTransaction);

    boolean saveDetail(int idTransaction, TransactionDetail detail);
}
