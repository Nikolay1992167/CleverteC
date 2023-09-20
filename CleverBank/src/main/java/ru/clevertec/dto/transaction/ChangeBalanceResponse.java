package ru.clevertec.dto.transaction;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import ru.clevertec.dto.adapter.LocalDateAdapter;
import ru.clevertec.dto.adapter.LocalTimeAdapter;
import ru.clevertec.entity.Currency;
import ru.clevertec.entity.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ChangeBalanceResponse(@SerializedName("transaction_id")
                                    Long transactionId,

                                    @JsonAdapter(LocalDateAdapter.class)
                                    LocalDate date,

                                    @JsonAdapter(LocalTimeAdapter.class)
                                    LocalTime time,

                                    Currency currency,
                                    Type type,

                                    @SerializedName("bank_sender_name")
                                    String bankSenderName,

                                    @SerializedName("bank_recipient_name")
                                    String bankRecipientName,

                                    @SerializedName("account_recipient_id")
                                    String accountRecipientId,

                                    BigDecimal sum,

                                    @SerializedName("old_balance")
                                    BigDecimal oldBalance,

                                    @SerializedName("new_balance")
                                    BigDecimal newBalance) {
}