package ru.clevertec.dao;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.dao.db.DatabaseConnection;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Bank;
import ru.clevertec.entity.User;
import ru.clevertec.exception.BankNotFoundException;
import ru.clevertec.exception.ResourceSqlException;
import ru.clevertec.exception.UserNotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ru.clevertec.dao.util.AccountSQLUtil.*;

@RequiredArgsConstructor
public class AccountDAOImpl implements AccountDAO {

    private final BankDAO bankDAO;
    private final UserDAO userDAO;

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException, IOException, ClassNotFoundException {
        Long id = resultSet.getLong("id");
        String currency = resultSet.getString("currency");
        LocalDate dateOpen = LocalDate.parse((CharSequence) resultSet.getTimestamp("date_open"));
        String number = resultSet.getString("number");
        BigDecimal balance = resultSet.getBigDecimal("balance");
        Long bankId = resultSet.getLong("bank_id");
        Long userId = resultSet.getLong("user_id");
        Bank bank = bankDAO.getBankById(bankId).orElseThrow(() -> new BankNotFoundException(bankId));
        User user = userDAO.getUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new Account(id, currency, dateOpen, number, balance, bank, user);
    }

    @Override
    public List<Account> getAllAccounts() {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ACCOUNTS)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                final List<Account> accounts = new ArrayList<>();
                while (resultSet.next()) {
                    Account account = createAccountFromResultSet(resultSet);
                    accounts.add(account);
                }
                return accounts;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = createAccountFromResultSet(resultSet);
                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> getAccountByNumber(String number) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_NUMBER)
        ) {
            statement.setString(1, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = createAccountFromResultSet(resultSet);
                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAccount(Account account) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_NEW_ACCOUNT, RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, account.getCurrency());
            statement.setTimestamp(2, Timestamp.valueOf(account.getDateOpen().atStartOfDay()));
            statement.setString(3, account.getNumber());
            statement.setBigDecimal(4, account.getBalance());
            statement.setLong(5, account.getBank().getId());
            statement.setLong(6, account.getUser().getId());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new ResourceSqlException("Creating account failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new ResourceSqlException("Creating account failed, no ID obtained.");
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)
        ) {
            statement.setString(1, account.getCurrency());
            statement.setTimestamp(2, Timestamp.valueOf(account.getDateOpen().atStartOfDay()));
            statement.setString(3, account.getNumber());
            statement.setBigDecimal(4, account.getBalance());
            statement.setLong(5, account.getBank().getId());
            statement.setLong(6, account.getUser().getId());
            statement.setLong(7, account.getId());
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT_BY_ID)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

