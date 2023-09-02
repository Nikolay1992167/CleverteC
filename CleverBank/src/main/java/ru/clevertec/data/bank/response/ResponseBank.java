package ru.clevertec.data.bank.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseBank {
    private Long id;
    private String title;
    private String bic;
}
