package ru.clevertec.dto.account;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import ru.clevertec.dto.adapter.LocalDateAdapter;
import ru.clevertec.entity.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountResponse(String id,
                              Currency currency,
                              BigDecimal balance,
                              @JsonAdapter(LocalDateAdapter.class)
                              @SerializedName("opening_date")
                              LocalDate openingDate,
                              @JsonAdapter(LocalDateAdapter.class)
                              @SerializedName("closing_date")
                              LocalDate closingDate,
                              BankResponse bank,
                              UserResponse user) {
}
