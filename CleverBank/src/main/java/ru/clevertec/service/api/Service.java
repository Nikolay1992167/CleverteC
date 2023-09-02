package ru.clevertec.service.api;

import ru.clevertec.data.transaction.request.RequestTransaction;

import java.sql.SQLException;

public interface Service {
    /**
     * Performs replenishment of the account according to the specified amount and account number.
     * @param requestTransaction - prepared object containing transaction type, account number and amount.
     */
    void depositMoney(RequestTransaction requestTransaction);

    /**
     * Performs withdrawal from the account according to the specified amount and account number.
     * @param requestTransaction - prepared object containing transaction type, account number and amount.
     */
    void withdrawMoney(RequestTransaction requestTransaction);

    /**
     * Performs the transfer of funds according to the specified accounts and the transaction amount.
     * @param requestTransaction - prepared object containing transaction type, account numbers and transaction amount.
     */
    void transferMoney(RequestTransaction requestTransaction) throws SQLException;

    /**
     * Performs interest accrual according to the specified interest rate at the end of the month.
     */
    void accrueInterest();
}
