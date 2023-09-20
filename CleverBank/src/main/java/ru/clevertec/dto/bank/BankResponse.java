package ru.clevertec.dto.bank;

import com.google.gson.annotations.SerializedName;

public record BankResponse(Long id,
                           String name,
                           String address,
                           @SerializedName("bic")
                           String bic) {
}
