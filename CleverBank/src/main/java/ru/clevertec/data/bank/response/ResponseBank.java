package ru.clevertec.data.bank.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.Account;

import java.util.List;

@Data
@Builder
public class ResponseBank {
    private Long id;
    private String name;
    private String bic;
}
