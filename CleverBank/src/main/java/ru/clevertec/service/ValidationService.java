package ru.clevertec.service;

import com.google.gson.Gson;
import ru.clevertec.entity.Currency;
import ru.clevertec.entity.Type;
import ru.clevertec.exception.handler.Violation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ValidationService {

    void validateAccountForClosingDate(LocalDate closingDate, String accountId);

    void validateAccountForCurrency(Currency senderCurrency, Currency recipientCurrency);

    void validateAccountForSufficientBalance(Type type, BigDecimal sum, BigDecimal oldBalance);

    void validateFieldByPattern(String field, String fieldName, String patternString, List<Violation> violations);

    void validateRequestForNull(Object object, String requestName, Gson gson);

    void validateBigDecimalFieldForPositive(BigDecimal field, String fieldName, List<Violation> violations);

    void validateLongFieldForPositive(Long field, String fieldName, List<Violation> violations);

}
