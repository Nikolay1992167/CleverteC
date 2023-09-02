package ru.clevertec.dao;

import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.dao.db.DatabaseConnection;
import ru.clevertec.entity.Bank;
import ru.clevertec.exception.ResourceSqlException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ru.clevertec.dao.util.BankSQLUtil.*;

public class BankDAOImpl implements BankDAO {

    private Bank createBankFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String bic = resultSet.getString("bic");
        return new Bank(id, title, bic);
    }

    @Override
    public List<Bank> getAllBanks() {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BANKS)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                final List<Bank> banks = new ArrayList<>();
                while (resultSet.next()) {
                    Bank bank = createBankFromResultSet(resultSet);
                    banks.add(bank);
                }
                return banks;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Bank> getBankById(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(SELECT_BANK_FROM_BY_ID)
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Bank bank = createBankFromResultSet(resultSet);
                    return Optional.of(bank);
                } else {
                    return Optional.empty();
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBank(Bank bank) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_NEW_BANK, RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, bank.getTitle());
            statement.setString(2, bank.getBic());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new ResourceSqlException("Creating bank failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new ResourceSqlException("Creating bank failed, no ID obtained.");
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBank(Bank bank) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BANK)
        ) {
            statement.setString(1, bank.getTitle());
            statement.setString(2, bank.getBic());
            statement.setLong(3, bank.getId());
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBank(Long id) {
        try (Connection connection = DatabaseConnection.initializeDatabase();
             PreparedStatement statement = connection.prepareStatement(DELETE_BANK_BY_ID)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

