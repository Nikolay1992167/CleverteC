package ru.clevertec.dao;


import ru.clevertec.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {

    Optional<Account> findById(String id);

    List<Account> findAll();

    Account save(Account account);

    Account update(Account account);

    Optional<Account> delete(String id);

}
