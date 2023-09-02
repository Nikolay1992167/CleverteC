package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;
import ru.clevertec.entity.Bank;
import ru.clevertec.exception.BankNotFoundException;
import ru.clevertec.mapper.BankMapper;
import ru.clevertec.service.api.BankService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankDAO bankDAO;
    private final BankMapper bankMapper;

    @Override
    public List<ResponseBank> getAllBanks() throws SQLException, IOException, ClassNotFoundException {
        return bankDAO.getAllBanks().stream()
                .map(bankMapper::buildBankResponse)
                .toList();
    }

    @Override
    public ResponseBank getBankById(Long id) {
        Optional<Bank> optionalBank = bankDAO.getBankById(id);
        Bank bank = optionalBank.orElseThrow(() -> new BankNotFoundException(id));
        return bankMapper.buildBankResponse(bank);
    }

    @Override
    public void addBank(RequestBank requestBank) {
        Bank bank = bankMapper.buildBank(requestBank);
        bankDAO.addBank(bank);
    }

    @Override
    public void updateBank(Long id, RequestBank updateRequestBank) {
        Bank bank = bankMapper.buildBank(updateRequestBank);
        bank.setId(id);
        bankDAO.updateBank(bank);
    }

    @Override
    public void deleteBank(Long id) {
        bankDAO.deleteBank(id);
    }
}
