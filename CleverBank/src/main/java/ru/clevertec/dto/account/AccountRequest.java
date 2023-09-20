package ru.clevertec.dto.account;

import com.google.gson.annotations.SerializedName;
import ru.clevertec.entity.Currency;

import java.math.BigDecimal;

public record AccountRequest(Currency currency,
                             BigDecimal balance,
                             @SerializedName("bank_id")
                             Long bankId,
                             @SerializedName("user_id")
                             Long userId) {
}
