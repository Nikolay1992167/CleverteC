package ru.clevertec.dao.api;

import ru.clevertec.entity.Transaction;
import ru.clevertec.exception.TransactionNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TransactionDAO {
    /**
     * Find all Transaction
     *
     * @return List of all Transactions
     */
    List<Transaction> getAllTransactions();

    /**
     * Returns saved Transaction
     *
     * @param id - Transaction id
     * @return - Transaction if contains
     * @throws TransactionNotFoundException - if not found
     */
    Optional<Transaction> getTransactionById(Long id);

    /**
     * Save a new Transactional
     *
     * @param transaction new Transaction without id
     */
    void addTransaction(Transaction transaction);

    /**
     * Update current Transaction
     *
     * @param transaction - updated
     */
    void updateTransaction(Transaction transaction);

    /**
     * Delete Transaction by ID
     *
     * @param id the Transaction ID
     */
    void deleteTransaction(Long id);
}
