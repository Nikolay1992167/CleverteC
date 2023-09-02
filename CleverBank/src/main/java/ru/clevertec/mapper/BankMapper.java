package ru.clevertec.mapper;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;
import ru.clevertec.entity.Bank;

@Data
@Builder
@NoArgsConstructor
public class BankMapper {

    public ResponseBank buildBankResponse(Bank bank) {
        return ResponseBank.builder()
                .id(bank.getId())
                .title(bank.getTitle())
                .bic(bank.getBic())
                .build();
    }

    public Bank buildBank(RequestBank requestBank) {
        return Bank.builder()
                .title(requestBank.getTitle())
                .bic(requestBank.getBic())
                .build();
    }
}
