package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.data.transaction.response.ResponseTransaction;
import ru.clevertec.entity.Transaction;
import ru.clevertec.exception.TransactionNotFoundException;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.service.api.TransactionService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final TransactionMapper transactionMapper;

    @Override
    public List<ResponseTransaction> getAllTransactions() {
        return transactionDAO.getAllTransactions().stream()
                .map(transactionMapper::buildTransactionResponse)
                .toList();
    }

    @Override
    public ResponseTransaction getTransactionById(Long id) {
        Optional<Transaction> optionalTransaction = transactionDAO.getTransactionById(id);
        Transaction transaction = optionalTransaction.orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.buildTransactionResponse(transaction);
    }

    @Override
    public void addTransaction(RequestTransaction requestTransaction) {
        Transaction transaction = transactionMapper.buildTransaction(requestTransaction);
        transactionDAO.addTransaction(transaction);
    }

    @Override
    public void updateTransaction(Long id, RequestTransaction requestTransaction) {
        Transaction transaction = transactionMapper.buildTransaction(requestTransaction);
        transaction.setId(id);
        transactionDAO.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionDAO.deleteTransaction(id);
    }
}
