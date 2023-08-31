package ru.clevertec.data.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResponseAccount {
    private Long id;
    private String number;
    private BigDecimal balance;
    private String bankTitle;
    private String userName;
}
