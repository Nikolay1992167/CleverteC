package ru.clevertec.dto.transaction;

import com.google.gson.annotations.SerializedName;
import ru.clevertec.entity.Type;

import java.math.BigDecimal;

public record ChangeBalanceRequest(@SerializedName("account_sender_id")
                                   String accountSenderId,
                                   @SerializedName("account_recipient_id")
                                   String accountRecipientId,
                                   BigDecimal sum,
                                   Type type) {
}
