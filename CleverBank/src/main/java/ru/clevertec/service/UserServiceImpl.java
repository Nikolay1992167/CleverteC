package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;
import ru.clevertec.entity.User;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.service.api.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Override
    public List<ResponseUser> getAllUsers() {
        return userDAO.getAllUsers().stream()
                .map(userMapper::buildUserResponse)
                .toList();
    }

    @Override
    public ResponseUser getUserById(Long id) {
        Optional<User> optionalUser = userDAO.getUserById(id);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.buildUserResponse(user);
    }

    @Override
    public void addUser(RequestUser requestUser) {
        User user = userMapper.buildUser(requestUser);
        userDAO.addUser(user);
    }

    @Override
    public void updateUser(Long id, RequestUser updateRequestUser) {
        User user = userMapper.buildUser(updateRequestUser);
        user.setId(id);
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}


