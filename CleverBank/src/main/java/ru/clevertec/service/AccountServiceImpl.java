package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;
import ru.clevertec.entity.Account;
import ru.clevertec.exception.AccountNotFoundException;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.service.api.AccountService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;

    @Override
    public List<ResponseAccount> geAllAccounts() {
        return accountDAO.getAllAccounts().stream()
                .map(accountMapper::buildAccountResponse)
                .toList();
    }

    @Override
    public ResponseAccount getAccountById(Long id) {
        Optional<Account> optionalAccount = accountDAO.getAccountById(id);
        Account account = optionalAccount.orElseThrow(() -> new AccountNotFoundException(id));
        return accountMapper.buildAccountResponse(account);
    }

    @Override
    public void addAccount(RequestAccount requestAccount) {
        Account account = accountMapper.buildAccount(requestAccount);
        accountDAO.addAccount(account);
    }

    @Override
    public void updateAccount(Long id, RequestAccount updateRequestAccount) {
        Account account = accountMapper.buildAccount(updateRequestAccount);
        account.setId(id);
        accountDAO.updateAccount(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountDAO.deleteAccount(id);
    }
}
