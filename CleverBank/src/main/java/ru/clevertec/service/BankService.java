package ru.clevertec.service;


import ru.clevertec.dto.bank.BankRequest;
import ru.clevertec.dto.bank.BankResponse;
import ru.clevertec.dto.util.DeleteResponse;
import ru.clevertec.entity.Bank;

import java.util.List;

public interface BankService {

    Bank findById(Long id);

    BankResponse findByIdResponse(Long id);

    List<BankResponse> findAll();

    BankResponse save(BankRequest request);

    BankResponse update(Long id, BankRequest request);

    DeleteResponse delete(Long id);

}
