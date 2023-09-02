package ru.clevertec.service.api;

import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;

import java.util.List;

public interface UserService {
    List<ResponseUser> getAllUsers();
    ResponseUser getUserById(Long id);
    void addUser(RequestUser userDto);
    void updateUser(Long id, RequestUser requestUser);
    void deleteUser(Long id);
}
