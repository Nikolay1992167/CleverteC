package ru.clevertec.data.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.TypeTransaction;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ResponseTransaction {
    private Long id;
    private TypeTransaction typeTransaction;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String date;
}
