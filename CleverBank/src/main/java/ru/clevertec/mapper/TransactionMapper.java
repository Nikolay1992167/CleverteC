package ru.clevertec.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.data.transaction.response.ResponseTransaction;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Transaction;
import ru.clevertec.exception.TransactionNotFoundException;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionMapper {

    private AccountDAO accountDAO;

    public ResponseTransaction buildTransactionResponse(Transaction transaction) {
        return ResponseTransaction.builder()
                .id(transaction.getId())
                .typeTransaction(transaction.getTypeTransaction())
                .fromAccountNumber(transaction.getFromAccount().getNumber())
                .toAccountNumber(transaction.getToAccount().getNumber())
                .amount(transaction.getAmount())
                .date(String.valueOf(transaction.getDate()))
                .build();
    }

    public Transaction buildTransaction(RequestTransaction requestTransaction) {
        switch (requestTransaction.getTypeTransaction()) {
            case DEPOSIT -> {
                return Transaction.builder()
                        .typeTransaction(requestTransaction.getTypeTransaction())
                        .toAccount(getAccount(requestTransaction.getAccountNumber()))
                        .amount(requestTransaction.getAmount())
                        .date(LocalDateTime.now())
                        .build();
            }
            case WITHDRAWAL -> {
                return Transaction.builder()
                        .typeTransaction(requestTransaction.getTypeTransaction())
                        .fromAccount(getAccount(requestTransaction.getAccountNumber()))
                        .amount(requestTransaction.getAmount())
                        .date(LocalDateTime.now())
                        .build();
            }
            case TRANSFER -> {
                String[] accountNumbers = requestTransaction.getAccountNumber().split(",");
                if (accountNumbers.length != 2) {
                    throw new IllegalArgumentException("Invalid account number format for transfer!");
                }
                return Transaction.builder()
                        .typeTransaction(requestTransaction.getTypeTransaction())
                        .fromAccount(getAccount(accountNumbers[0].trim()))
                        .toAccount(getAccount(accountNumbers[1].trim()))
                        .amount(requestTransaction.getAmount())
                        .date(LocalDateTime.now())
                        .build();
            }
            default -> throw new IllegalArgumentException("Unknown transaction type!");
        }
    }

    private Account getAccount(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber)
                .orElseThrow(() -> new TransactionNotFoundException(accountNumber));
    }
}
