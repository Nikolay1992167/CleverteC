package ru.clevertec.service.api;


import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BankService {
    List<ResponseBank> getAllBanks() throws SQLException, IOException, ClassNotFoundException;

    ResponseBank getBankById(Long id);

    void addBank(RequestBank bankDto);

    void updateBank(Long id, RequestBank requestBank);

    void deleteBank(Long id);
}
