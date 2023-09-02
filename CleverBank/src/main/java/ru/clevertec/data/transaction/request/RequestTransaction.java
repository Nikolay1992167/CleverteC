package ru.clevertec.data.transaction.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.entity.TypeTransaction;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTransaction {
    private TypeTransaction typeTransaction;
    private String accountNumber;
    private BigDecimal amount;
    private String date;
}
