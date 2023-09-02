package ru.clevertec.service.api;

import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;

import java.util.List;

public interface UserService {

    /**
     * Find all Users
     *
     * @return List of all ResponseUser formed UserMapper
     */
    List<ResponseUser> getAllUsers();

    /**
     * Find User by id
     *
     * @param id - User id
     * @return - ResponseUser formed UserMapper
     */
    ResponseUser getUserById(Long id);

    /**
     * Save a new User
     *
     * @param requestUser prepared  object without id
     */
    void addUser(RequestUser requestUser);

    /**
     * Update User
     * @param id User ID
     * @param requestUser prepared  object without id
     */
    void updateUser(Long id, RequestUser requestUser);

    /**
     * Delete User by ID
     *
     * @param id User ID
     */
    void deleteUser(Long id);
}
