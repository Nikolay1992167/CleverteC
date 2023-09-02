package ru.clevertec.service.api;

import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;

import java.util.List;

public interface AccountService {
    /**
     * Find all Accounts
     *
     * @return List of all ResponseAccount formed AccountMapper
     */
    List<ResponseAccount> geAllAccounts();

    /**
     * Find Account by id
     *
     * @param id - Account id
     * @return - ResponseAccount formed AccountMapper
     */
    ResponseAccount getAccountById(Long id);

    /**
     * Save a new Account
     *
     * @param requestAccount prepared  object without id
     */
    void addAccount(RequestAccount requestAccount);

    /**
     * Update Account
     *
     * @param id             Account ID
     * @param requestAccount prepared  object without id
     */
    void updateAccount(Long id, RequestAccount requestAccount);

    /**
     * Delete Account by ID
     *
     * @param id Account ID
     */
    void deleteAccount(Long id);
}
