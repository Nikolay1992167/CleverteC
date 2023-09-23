package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.bank.BankRequest;
import ru.clevertec.dto.bank.BankResponse;
import ru.clevertec.entity.Bank;

import java.util.List;

@Mapper
public interface BankMapper {

    BankResponse toResponse(Bank bank);

    List<BankResponse> toResponseList(List<Bank> banks);

    Bank fromRequest(BankRequest request);

}
