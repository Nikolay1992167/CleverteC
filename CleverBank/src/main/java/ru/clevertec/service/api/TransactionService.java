package ru.clevertec.service.api;

import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.data.transaction.response.ResponseTransaction;

import java.util.List;

public interface TransactionService {
    List<ResponseTransaction> getAllTransactions();

    ResponseTransaction getTransactionById(Long id);

    void addTransaction(RequestTransaction requestTransaction);

    void updateTransaction(Long id, RequestTransaction requestTransaction);

    void deleteTransaction(Long id);
}
