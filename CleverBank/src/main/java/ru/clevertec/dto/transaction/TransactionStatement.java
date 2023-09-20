package ru.clevertec.dto.transaction;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import ru.clevertec.dto.adapter.LocalDateAdapter;
import ru.clevertec.entity.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionStatement(@JsonAdapter(LocalDateAdapter.class)
                                   LocalDate date,

                                   Type type,

                                   @SerializedName("user_lastname")
                                   String userLastname,

                                   BigDecimal sum) {
}
