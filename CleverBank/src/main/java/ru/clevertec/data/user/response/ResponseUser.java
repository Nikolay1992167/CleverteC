package ru.clevertec.data.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseUser {
    private Long id;
    private String name;
    private String email;
    private String password;
}
