package ru.clevertec.service.api;


import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BankService {
    /**
     * Find all Banks
     *
     * @return List of all ResponseBank formed BankMapper
     */
    List<ResponseBank> getAllBanks() throws SQLException, IOException, ClassNotFoundException;

    /**
     * Find Bank by id
     *
     * @param id - Bank id
     * @return - ResponseAccount formed BankMapper
     */
    ResponseBank getBankById(Long id);

    /**
     * Save a new Bank
     *
     * @param requestBank prepared  object without id
     */
    void addBank(RequestBank requestBank);

    /**
     * Update Bank
     * @param id Account ID
     * @param requestBank prepared  object without id
     */
    void updateBank(Long id, RequestBank requestBank);

    /**
     * Delete Account by ID
     *
     * @param id Account ID
     */
    void deleteBank(Long id);
}
