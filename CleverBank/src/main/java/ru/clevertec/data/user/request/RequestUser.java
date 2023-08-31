package ru.clevertec.data.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class RequestUser {
    private String name;
    private String email;
    private String password;
}
