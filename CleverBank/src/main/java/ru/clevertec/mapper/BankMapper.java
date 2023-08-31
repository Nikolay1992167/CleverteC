package ru.clevertec.mapper;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Bank;

import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
public class BankMapper {

    public ResponseBank buildBankResponse(Bank bank) {
        return ResponseBank.builder()
                .id(bank.getId())
                .name(bank.getTitle())
                .bic(bank.getBic())
                .build();
    }

    public Bank buildBank(RequestBank requestBank) {
        return Bank.builder()
                .title(requestBank.getName())
                .bic(requestBank.getBic())
                .build();
    }
}
