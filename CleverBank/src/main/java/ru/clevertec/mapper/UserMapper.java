package ru.clevertec.mapper;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;
import ru.clevertec.entity.User;

@Data
@Builder
@NoArgsConstructor
public class UserMapper {

    public ResponseUser buildUserResponse(User user) {
        return ResponseUser.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User buildUser(RequestUser requestUser) {
        return User.builder()
                .name(requestUser.getName())
                .email(requestUser.getEmail())
                .password(requestUser.getPassword())
                .build();
    }
}
