package ru.clevertec.service.api;

import ru.clevertec.data.transaction.request.RequestTransaction;

import java.sql.SQLException;

public interface Service {
    void depositMoney(RequestTransaction requestTransaction);

    void withdrawMoney(RequestTransaction requestTransaction);

    void transferMoney(RequestTransaction requestTransaction) throws SQLException;

    void accrueInterest();
}
