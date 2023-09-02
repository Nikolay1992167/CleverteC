package ru.clevertec.data.bank.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBank {
    private Long id;
    private String title;
    private String bic;
}
