package ru.clevertec.dao.api;

import ru.clevertec.entity.User;
import ru.clevertec.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    /**
     * Find all Users
     *
     * @return List of all Users
     */
    List<User> getAllUsers();

    /**
     * Returns saved User
     *
     * @param id - User id
     * @return - User if contains
     * @throws UserNotFoundException - if not found
     */
    Optional<User> getUserById(Long id);

    /**
     * Save new User
     *
     * @param user new User without id
     */
    void addUser(User user);

    /**
     * Update current User
     *
     * @param user - updated
     */
    void updateUser(User user);

    /**
     * Delete an User by ID
     *
     * @param id the User ID
     */
    void deleteUser(Long id);
}
