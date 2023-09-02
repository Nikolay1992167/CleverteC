package ru.clevertec.dao.util;

public class AccountSQLUtil {
    public static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";
    public static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    public static final String SELECT_ACCOUNT_BY_NUMBER = "SELECT * FROM accounts WHERE number = ?";
    public static final String INSERT_NEW_ACCOUNT = "INSERT INTO accounts (currency, date_open, number, balance, bank_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ACCOUNT = "UPDATE accounts SET currency = ?, date_open = ?, number = ?, balance = ?, bank_id = ?, user_id = ? WHERE id = ?";
    public static final String DELETE_ACCOUNT_BY_ID = "DELETE FROM accounts WHERE id = ?";
}
