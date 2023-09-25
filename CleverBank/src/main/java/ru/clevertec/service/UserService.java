package ru.clevertec.service;



import ru.clevertec.dto.user.UserRequest;
import ru.clevertec.dto.user.UserResponse;
import ru.clevertec.dto.util.DeleteResponse;
import ru.clevertec.entity.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    UserResponse findByIdResponse(Long id);

    List<UserResponse> findAll();

    UserResponse save(UserRequest request);

    UserResponse update(Long id, UserRequest request);

    DeleteResponse delete(Long id);

}
