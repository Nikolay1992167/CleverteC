package ru.clevertec.service.api;


import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BankService {
    List<ResponseBank> getAllBanks() throws SQLException, IOException, ClassNotFoundException;

    ResponseBank getBankById(Long id) throws SQLException, IOException, ClassNotFoundException;

    void addBank(RequestBank bankDto) throws SQLException, IOException, ClassNotFoundException;

    void updateBank(Long id, RequestBank requestBank) throws SQLException, IOException, ClassNotFoundException;

    void deleteBank(Long id) throws SQLException, IOException, ClassNotFoundException;
}
