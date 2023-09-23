package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.clevertec.dto.account.AccountRequest;
import ru.clevertec.dto.account.AccountResponse;
import ru.clevertec.entity.Account;

import java.util.List;

@Mapper
public interface AccountMapper {

    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "bank.id", source = "bankId")
    Account fromRequest(AccountRequest request);

}
