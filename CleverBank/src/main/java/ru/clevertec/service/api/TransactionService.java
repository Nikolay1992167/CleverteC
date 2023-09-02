package ru.clevertec.service.api;

import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.data.transaction.response.ResponseTransaction;

import java.util.List;

public interface TransactionService {
    /**
     * Find all Transactions
     *
     * @return List of all ResponseTransaction formed TransactionMapper
     */
    List<ResponseTransaction> getAllTransactions();

    /**
     * Find Transaction by id
     *
     * @param id - Transaction id
     * @return - ResponseTransaction formed TransactionMapper
     */
    ResponseTransaction getTransactionById(Long id);

    /**
     * Save a new Transaction
     *
     * @param requestTransaction prepared  object without id
     */
    void addTransaction(RequestTransaction requestTransaction);

    /**
     * Update Transaction
     * @param id Transaction ID
     * @param requestTransaction prepared  object without id
     */
    void updateTransaction(Long id, RequestTransaction requestTransaction);

    /**
     * Delete Transaction by ID
     *
     * @param id Transaction ID
     */
    void deleteTransaction(Long id);
}
