package ru.clevertec.dao;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.dao.db.DatabaseConnection;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Transaction;
import ru.clevertec.entity.TypeTransaction;
import ru.clevertec.exception.ResourceSqlException;
import ru.clevertec.exception.TransactionNotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ru.clevertec.dao.util.TransactionSQLUtil.*;

@RequiredArgsConstructor
public class TransactionDAOImpl implements TransactionDAO {
    private final AccountDAO accountDAO;

    private Transaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Long id = resultSet.getLong("id");
        TypeTransaction typeTransaction = TypeTransaction.valueOf(resultSet.getString("type_transaction"));
        String fromAccountNumber = resultSet.getString("from_account");
        String toAccountNumber = resultSet.getString("to_account");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
        Account fromAccount = fromAccountNumber != null ? accountDAO.getAccountByNumber(fromAccountNumber).orElse(null) : null;
        Account toAccount = toAccountNumber != null ? accountDAO.getAccountByNumber(toAccountNumber).orElse(null) : null;
        return new Transaction(id, typeTransaction, fromAccount, toAccount, amount, date);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TRANSACTION)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                final List<Transaction> transactions = new ArrayList<>();
                while (resultSet.next()) {
                    Transaction transaction = createTransactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
                return transactions;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_TRANSACTION_BY_ID)
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Transaction transaction = createTransactionFromResultSet(resultSet);
                    return Optional.of(transaction);
                } else {
                    return Optional.empty();
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new TransactionNotFoundException("Not ");
        }
    }

    @Override
    public void addTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_NEW_TRANSACTION, RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, transaction.getTypeTransaction().name());
            statement.setString(2, transaction.getFromAccount() != null ? transaction.getFromAccount().getNumber() : null);
            statement.setString(3, transaction.getToAccount() != null ? transaction.getToAccount().getNumber() : null);
            statement.setBigDecimal(4, transaction.getAmount());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new ResourceSqlException("Creating transaction failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new ResourceSqlException("Creating transaction failed, no ID obtained.");
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRANSACTION)
        ) {
            statement.setString(1, transaction.getTypeTransaction().name());
            statement.setString(2, transaction.getFromAccount() != null ? transaction.getFromAccount().getNumber() : null);
            statement.setString(3, transaction.getToAccount() != null ? transaction.getToAccount().getNumber() : null);
            statement.setBigDecimal(4, transaction.getAmount());
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTransaction(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(DELETE_TRANSACTION_BY_ID)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}