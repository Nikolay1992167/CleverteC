import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;
import ru.clevertec.entity.Account;
import ru.clevertec.entity.Bank;
import ru.clevertec.entity.User;
import ru.clevertec.exception.AccountNotFoundException;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.service.AccountServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @Mock
    private AccountDAO accountDAO;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void geAllAccountsTest() {
        List<Account> accounts = List.of(
                new Account(1L, "BYN", LocalDate.now(), "2486", new BigDecimal("1000"), new Bank(1L, "VTB", "BTV"), new User(1L, "Ivan Petrov", "petrov@mail.com", "vortep")),
                new Account(2L, "BYN", LocalDate.now(), "1793", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(2L, "Petr Ivanov", "ivanov@mail.com", "vonavi"))
        );
        List<ResponseAccount> expected = List.of(
                new ResponseAccount(1L, "BYN", LocalDateTime.now().toString(), "2486", new BigDecimal("1000"), "VTB", "Ivan Petrov"),
                new ResponseAccount(2L, "BYN", LocalDateTime.now().toString(), "1793", new BigDecimal("2000"), "VTB", "Petr Ivanov")
        );
        when(accountDAO.getAllAccounts()).thenReturn(accounts);
        when(accountMapper.buildAccountResponse(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            return expected.stream()
                    .filter(response -> response.getId().equals(account.getId()))
                    .findFirst()
                    .orElse(null);
        });
        List<ResponseAccount> actual = service.geAllAccounts();
        assertThat(actual).isEqualTo(expected);
        verify(accountDAO).getAllAccounts();
        verify(accountMapper, times(2)).buildAccountResponse(any(Account.class));
    }

    @Test
    void getAccountByIdTest() {
        Long id = 1L;
        Account account = new Account(1L, "BYN", LocalDate.now(), "2486", new BigDecimal("1000"), new Bank(1L, "VTB", "BTV"), new User(1L, "Ivan Petrov", "petrov@mail.com", "vortep"));
        ResponseAccount responseAccount = new ResponseAccount(1L, "BYN", LocalDateTime.now().toString(), "2486", new BigDecimal("1000"), "VTB", "Ivan Petrov");
        when(accountDAO.getAccountById(id)).thenReturn(Optional.of(account));
        when(accountMapper.buildAccountResponse(account)).thenReturn(responseAccount);
        ResponseAccount actual = service.getAccountById(id);
        assertThat(actual).isEqualTo(responseAccount);
        verify(accountDAO).getAccountById(id);
        verify(accountMapper).buildAccountResponse(account);
    }

    @Test
    void getAccountByIdTestThrowExceptionIfNotFound() {
        Long id = 1L;
        when(accountDAO.getAccountById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getAccountById(id))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account with id '1' not found!");
        verify(accountDAO).getAccountById(id);
        verifyNoInteractions(accountMapper);
    }

    @Test
    void addAccountTest() {
        RequestAccount requestAccount = new RequestAccount("17935", "BYN", String.valueOf(LocalDate.now()), new BigDecimal("1000"), 1L, 1L);
        Account account = new Account(null, "BYN", LocalDate.now(), "17935", new BigDecimal("1000"), new Bank(1L, "VTB", "BTV"), new User(1L, "Ivan Petrov", "petrov@mail.com", "vortep"));
        when(accountMapper.buildAccount(requestAccount)).thenReturn(account);
        service.addAccount(requestAccount);
        verify(accountMapper).buildAccount(requestAccount);
        verify(accountDAO).addAccount(account);
    }

    @Test
    void updateAccountTest() {
        Long id = 1L;
        RequestAccount requestAccount = new RequestAccount("17935", "BYN", String.valueOf(LocalDate.now()), new BigDecimal("1000"), 1L, 1L);
        Account account = new Account(id, "BYN", LocalDate.now(), "17935", new BigDecimal("1000"), new Bank(1L, "VTB", "BTV"), new User(1L, "Ivan Petrov", "petrov@mail.com", "vortep"));
        when(accountMapper.buildAccount(requestAccount)).thenReturn(account);
        service.updateAccount(id, requestAccount);
        verify(accountMapper).buildAccount(requestAccount);
        verify(accountDAO).updateAccount(account);
    }

    @Test
    void deleteAccountTest() {
        Long id = 1L;
        service.deleteAccount(id);
        verify(accountDAO).deleteAccount(id);
    }
}
