package ru.clevertec.service;


import ru.clevertec.dto.transaction.AmountStatementResponse;
import ru.clevertec.dto.transaction.ChangeBalanceResponse;
import ru.clevertec.dto.transaction.TransactionStatementResponse;
import ru.clevertec.dto.transaction.TransferBalanceResponse;

public interface CheckService {

     String createChangeBalanceCheck(ChangeBalanceResponse response);

     String createTransferBalanceCheck(TransferBalanceResponse response);

     String createTransactionStatement(TransactionStatementResponse response);

     String createAmountStatement(AmountStatementResponse response);

}
