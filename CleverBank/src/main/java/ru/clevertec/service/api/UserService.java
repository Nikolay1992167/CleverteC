package ru.clevertec.service.api;

import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<ResponseUser> getAllUsers() throws SQLException, IOException, ClassNotFoundException;

    ResponseUser getUserById(Long id) throws SQLException, IOException, ClassNotFoundException;

    void addUser(RequestUser userDto) throws SQLException, IOException, ClassNotFoundException;

    void updateUser(Long id, RequestUser requestUser) throws SQLException, IOException, ClassNotFoundException;

    void deleteUser(Long id) throws SQLException, IOException, ClassNotFoundException;
}
