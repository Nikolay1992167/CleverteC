package ru.clevertec.mapper;

import lombok.*;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Bank;
import ru.clevertec.entity.User;
import ru.clevertec.exception.BankNotFoundException;
import ru.clevertec.exception.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
public class AccountMapper {

    private BankDAO bankDAO;
    private UserDAO userDAO;

    public ResponseAccount buildAccountResponse(Account account) {
        return ResponseAccount.builder()
                .id(account.getId())
                .number(account.getNumber())
                .balance(account.getBalance())
                .bankTitle(account.getBank().getTitle())
                .userName(account.getUser().getName())
                .build();
    }

    public Account buildAccount(RequestAccount requestAccount) {
        Bank bank = bankDAO.getBankById(requestAccount.getBankId())
                .orElseThrow(() -> new BankNotFoundException(requestAccount.getBankId()));
        User user = userDAO.getUserById(requestAccount.getUserId())
                .orElseThrow(() -> new UserNotFoundException(requestAccount.getUserId()));
        return Account.builder()
                .number(requestAccount.getNumber())
                .balance(requestAccount.getBalance())
                .bank(bank)
                .user(user)
                .build();
    }
}
