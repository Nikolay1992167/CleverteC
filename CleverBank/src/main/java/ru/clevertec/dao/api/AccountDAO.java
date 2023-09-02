package ru.clevertec.dao.api;

import ru.clevertec.entity.Account;
import ru.clevertec.exception.AccountNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    /**
     * Find all Accounts
     *
     * @return List of all Accounts
     */
    List<Account> getAllAccounts();

    /**
     * Returns saved account
     *
     * @param id - Account id
     * @return - Account if contains
     * @throws AccountNotFoundException - if not found
     */
    Optional<Account> getAccountById(Long id);

    /**
     * Returns saved Account
     *
     * @param number- Account number
     * @return - Account if contains
     * @throws AccountNotFoundException - if not found
     */
    Optional<Account> getAccountByNumber(String number);

    /**
     * Save new Account
     *
     * @param account new Account without id
     */
    void addAccount(Account account);

    /**
     * Update current Account
     *
     * @param account - updated
     */
    void updateAccount(Account account);

    /**
     * Delete Account by ID
     *
     * @param id Account ID
     */
    void deleteAccount(Long id);
}
