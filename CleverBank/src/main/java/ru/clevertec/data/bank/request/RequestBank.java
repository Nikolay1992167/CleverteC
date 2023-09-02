package ru.clevertec.data.bank.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestBank {
    private String title;
    private String bic;
}
