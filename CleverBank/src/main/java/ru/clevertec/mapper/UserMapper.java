package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.user.UserRequest;
import ru.clevertec.dto.user.UserResponse;
import ru.clevertec.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    User fromRequest(UserRequest request);

}
