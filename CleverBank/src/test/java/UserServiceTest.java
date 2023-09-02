import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.data.user.request.RequestUser;
import ru.clevertec.data.user.response.ResponseUser;
import ru.clevertec.entity.User;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersTest() {
        List<User> users = List.of(
                new User(8L, "Valja Brovkina", "brovkina@mail.com", "valja21"),
                new User(12L, "Kirill Nelson", "nelson@mail.com", "kiril")
        );
        List<ResponseUser> expected = List.of(
                new ResponseUser(8L, "Valja Brovkina", "brovkina@mail.com", "valja21"),
                new ResponseUser(12L, "Kirill Nelson", "nelson@mail.com", "kiril")
        );
        when(userDAO.getAllUsers()).thenReturn(users);
        when(userMapper.buildUserResponse(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return new ResponseUser(user.getId(), user.getName(), user.getEmail(), user.getPassword());
        });
        List<ResponseUser> actual = userService.getAllUsers();
        assertThat(actual).isEqualTo(expected);
        verify(userDAO).getAllUsers();
        verify(userMapper, times(2)).buildUserResponse(any(User.class));
    }

    @Test
    void getUserByIdTest() {
        Long id = 3L;
        User user = new User(id, "Nata Vedro", "vedro@Mail.com", "ordev");
        ResponseUser expected = new ResponseUser(id, "Nata Vedro", "vedro@Mail.com", "ordev");
        when(userDAO.getUserById(id)).thenReturn(Optional.of(user));
        when(userMapper.buildUserResponse(user)).thenReturn(expected);
        ResponseUser actual = userService.getUserById(id);
        assertThat(actual).isEqualTo(expected);
        verify(userDAO).getUserById(id);
        verify(userMapper).buildUserResponse(user);
    }

    @Test
    void getUserByIdTrowExceptionIfNotExists() {
        Long id = 3L;
        when(userDAO.getUserById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id '3' not found!");
        verify(userDAO).getUserById(id);
        verifyNoInteractions(userMapper);
    }

    @Test
    void addUserTest() {
        RequestUser requestUser = new RequestUser("Pavel Egorov", "egorov@mail.com", "voroge");
        User user = new User(null, "Pavel Egorov", "egorov@mail.com", "voroge");
        when(userMapper.buildUser(requestUser)).thenReturn(user);
        userService.addUser(requestUser);
        verify(userMapper).buildUser(requestUser);
        verify(userDAO).addUser(user);
    }

    @Test
    void updateUserTest() {
        Long id = 8L;
        RequestUser requestUser = new RequestUser("Ivan Tor", "tor@mail.com", "topolina");
        User user = new User(null, "Ivan Tor", "tor@mail.com", "topolina");
        when(userMapper.buildUser(requestUser)).thenReturn(user);
        userService.updateUser(id, requestUser);
        verify(userMapper).buildUser(requestUser);
        verify(userDAO).updateUser(user);
    }

    @Test
    void deleteUserTest() {
        Long id = 5L;
        userService.deleteUser(id);
        verify(userDAO).deleteUser(id);
    }
}
