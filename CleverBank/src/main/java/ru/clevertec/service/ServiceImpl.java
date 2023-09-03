package ru.clevertec.service;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Transaction;
import ru.clevertec.entity.TypeTransaction;
import ru.clevertec.exception.AccountNotFoundException;
import ru.clevertec.exception.AmountFundsException;
import ru.clevertec.exception.InitializationException;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.receipt.ReceiptPrinter;
import ru.clevertec.service.api.Service;
import ru.clevertec.service.appsettings.AppSettings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
@RequiredArgsConstructor
public class ServiceImpl implements Service {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final TransactionMapper transactionMapper;
    private final ScheduledExecutorService executor;

    private final Long interestRate;
    private final Long period = new AppSettings().getPeriod();

    public ServiceImpl(AccountDAO accountDAO, TransactionDAO transactionDAO, TransactionMapper transactionMapper) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.transactionMapper = transactionMapper;
        interestRate = new AppSettings().getInterestRate();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::accrueInterest, 0, period, TimeUnit.SECONDS);
    }

    @Override
    public void depositMoney(@NonNull RequestTransaction requestTransaction) {
        BigDecimal amount = requestTransaction.getAmount();
        String accountNumber = requestTransaction.getAccountNumber();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountFundsException("Invalid amount!");
        }
        Transaction transaction;
        synchronized (accountDAO) {
            synchronized (transactionDAO) {
                Optional<Account> accountOptional = accountDAO.getAccountByNumber(accountNumber);
                Account account = accountOptional.orElseThrow(() -> new AccountNotFoundException(accountNumber));
                account.setBalance(account.getBalance().add(amount));
                accountDAO.updateAccount(account);
                transaction = transactionMapper.buildTransaction(requestTransaction);
                transactionDAO.addTransaction(transaction);
                ReceiptPrinter.printReceipt(transaction);
            }
        }
    }

    @Override
    public void withdrawMoney(@NonNull RequestTransaction requestTransaction) {
        BigDecimal amount = requestTransaction.getAmount();
        String accountNumber = requestTransaction.getAccountNumber();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountFundsException("Invalid amount!");
        }
        Transaction transaction;
        synchronized (accountDAO) {
            synchronized (transactionDAO) {
                Optional<Account> accountOptional = accountDAO.getAccountByNumber(accountNumber);
                Account account = accountOptional.orElseThrow(() -> new AccountNotFoundException(accountNumber));
                if (account.getBalance().compareTo(amount) < 0) {
                    throw new AmountFundsException("Insufficient funds!");
                }
                account.setBalance(account.getBalance().subtract(amount));
                accountDAO.updateAccount(account);
                transaction = transactionMapper.buildTransaction(requestTransaction);
                transactionDAO.addTransaction(transaction);
                ReceiptPrinter.printReceipt(transaction);
            }
        }
    }

    @Override
    public void transferMoney(@NonNull RequestTransaction requestTransaction) {
        BigDecimal amount = requestTransaction.getAmount();
        String[] accountNumbers = requestTransaction.getAccountNumber().split(",");
        if (accountNumbers.length != 2) {
            throw new IllegalArgumentException("Invalid account number format for transfer!");
        }
        String fromAccountNumber = accountNumbers[0].trim();
        String toAccountNumber = accountNumbers[1].trim();

        Transaction transaction;
        synchronized (accountDAO) {
            synchronized (transactionDAO) {
                Account fromAccount = accountDAO.getAccountByNumber(fromAccountNumber).orElseThrow(() -> new AccountNotFoundException(fromAccountNumber));
                Account toAccount = accountDAO.getAccountByNumber(toAccountNumber).orElseThrow(() -> new AccountNotFoundException(toAccountNumber));
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new AmountFundsException("Invalid amount!");
                }
                fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
                toAccount.setBalance(toAccount.getBalance().add(amount));
                accountDAO.updateAccount(fromAccount);
                accountDAO.updateAccount(toAccount);
                transaction = transactionMapper.buildTransaction(requestTransaction);
                ReceiptPrinter.printReceipt(transaction);
                transactionDAO.addTransaction(transaction);
                ReceiptPrinter.printReceipt(transaction);
            }
        }
    }

    @Override
    public void accrueInterest() {
        TypeTransaction typeTransaction = TypeTransaction.DEPOSIT;
        List<Account> accounts = accountDAO.getAllAccounts();
        LocalDate today = LocalDate.now();
        for (Account account : accounts) {
            if (isLastDayOfMonth(today)) {
                String numberAccount = account.getNumber();
                BigDecimal interestRate = BigDecimal.valueOf(getInterestRate()).divide(BigDecimal.valueOf(100));
                BigDecimal interest = account.getBalance().multiply(BigDecimal.ONE.add(interestRate)).setScale(2, RoundingMode.HALF_UP);
                synchronized (accountDAO) {
                    synchronized (transactionDAO) {
                        account.setBalance(account.getBalance().add(interest));
                        accountDAO.updateAccount(account);
                        RequestTransaction transactionAccrue = new RequestTransaction(typeTransaction, numberAccount, interest, String.valueOf(LocalDateTime.now()));
                        Transaction transaction = transactionMapper.buildTransaction(transactionAccrue);
                        transactionDAO.addTransaction(transaction);
                        ReceiptPrinter.printReceipt(transaction);
                    }
                }
            } else {
                throw new InitializationException("This operation is performed on the last day of the month!");
            }
        }
    }

    public boolean isLastDayOfMonth(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        int lengthOfMonth = date.lengthOfMonth();
        return dayOfMonth == lengthOfMonth;
    }
}
