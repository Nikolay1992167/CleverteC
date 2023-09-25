package ru.clevertec.service;


import ru.clevertec.dto.account.AccountRequest;
import ru.clevertec.dto.account.AccountResponse;
import ru.clevertec.dto.util.DeleteResponse;
import ru.clevertec.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account findById(String id) throws AccountNotFoundException;

    AccountResponse findByIdResponse(String id) throws AccountNotFoundException;

    List<Account> findAll();

    List<AccountResponse> findAllResponses();

    AccountResponse save(AccountRequest request);

    Account updateBalance(Account account, BigDecimal balance);

    AccountResponse closeAccount(String id) throws AccountNotFoundException;

    DeleteResponse delete(String id) throws AccountNotFoundException;

}
