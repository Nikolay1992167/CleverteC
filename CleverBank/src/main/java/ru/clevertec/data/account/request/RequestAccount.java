package ru.clevertec.data.account.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAccount {
    private String number;
    private String currency;
    private String dateOpen;
    private BigDecimal balance;
    private Long bankId;
    private Long userId;
}
