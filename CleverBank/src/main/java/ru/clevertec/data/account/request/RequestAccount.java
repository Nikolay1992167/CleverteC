package ru.clevertec.data.account.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RequestAccount {
    private String number;
    private BigDecimal balance;
    private Long bankId;
    private Long userId;
}
