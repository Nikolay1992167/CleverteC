package ru.clevertec.dto.user;

import com.google.gson.annotations.SerializedName;

public record UserRequest(String lastname,
                          String firstname,
                          String surname,
                          @SerializedName("mobile_number")
                          String mobileNumber) {
}
