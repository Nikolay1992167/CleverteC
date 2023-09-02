package ru.clevertec.dao.util;

public class TransactionSQLUtil {
    public static final String SELECT_ALL_TRANSACTION = "SELECT * FROM transactions";
    public static final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactions WHERE id = ?";
    public static final String INSERT_NEW_TRANSACTION = "INSERT INTO transactions (type_transaction, from_account, to_account, amount, date) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_TRANSACTION = "UPDATE transactions SET from_account = ?, to_account = ?, amount = ?, date = ? WHERE id = ?";
    public static final String DELETE_TRANSACTION_BY_ID = "DELETE FROM transactions WHERE id = ?";
}
