package ru.clevertec.service;



import ru.clevertec.dto.transaction.*;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface TransactionService {

    ChangeBalanceResponse changeBalance(ChangeBalanceRequest request) throws AccountNotFoundException;

    TransferBalanceResponse transferBalance(TransferBalanceRequest request) throws SQLException;

    TransactionStatementResponse findAllByPeriodOfDateAndAccountId(TransactionStatementRequest request) throws AccountNotFoundException;

    AmountStatementResponse findSumOfFundsByPeriodOfDateAndAccountId(TransactionStatementRequest request) throws AccountNotFoundException;

    TransactionResponse findById(Long id);

    List<TransactionResponse> findAllBySendersAccountId(String id);

    List<TransactionResponse> findAllByRecipientAccountId(String id);

}
