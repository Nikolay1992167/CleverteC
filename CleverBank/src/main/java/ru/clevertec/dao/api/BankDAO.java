package ru.clevertec.dao.api;

import ru.clevertec.entity.Bank;
import ru.clevertec.exception.BankNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BankDAO {
    /**
     * Find all Banks
     *
     * @return List of all Banks
     */
    List<Bank> getAllBanks() throws SQLException, IOException, ClassNotFoundException;

    /**
     * Returns saved Bank
     *
     * @param id - Bank id
     * @return - Bank if contains
     * @throws BankNotFoundException - if not found
     */
    Optional<Bank> getBankById(Long id);

    /**
     * Save a new bank
     *
     * @param bank new Bank without id
     */
    void addBank(Bank bank);

    /**
     * Update current Bank
     *
     * @param bank - updated
     */
    void updateBank(Bank bank);

    /**
     * Delete a Bank by ID
     *
     * @param id the Bank ID
     */
    void deleteBank(Long id);
}
