package ru.clevertec.service.api;

import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;

import java.util.List;

public interface AccountService {
    List<ResponseAccount> geAllAccounts();
    ResponseAccount getAccountById(Long id);
    void addAccount(RequestAccount requestAccount);
    void updateAccount(Long id, RequestAccount requestAccount);
    void deleteAccount(Long id);
}
