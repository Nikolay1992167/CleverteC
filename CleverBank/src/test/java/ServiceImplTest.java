import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.entity.*;
import ru.clevertec.exception.AccountNotFoundException;
import ru.clevertec.exception.AmountFundsException;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.service.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class ServiceImplTest {
    @Mock
    private AccountDAO accountDAO;

    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private ServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void depositMoneyTest() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.DEPOSIT, "123", new BigDecimal("100"), "2021-09-21T10:00:00");
        Account account = new Account(5L, "BYN", LocalDate.now(), "65654", new BigDecimal("200"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3"));
        Transaction transaction = new Transaction(1L, TypeTransaction.DEPOSIT, null, account, new BigDecimal("100"), LocalDateTime.parse("2021-09-21T10:00:00"));
        when(accountDAO.getAccountByNumber("123")).thenReturn(Optional.of(account));
        when(transactionMapper.buildTransaction(request)).thenReturn(transaction);
        service.depositMoney(request);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal("300"));
        verify(accountDAO).updateAccount(account);
        verify(transactionDAO).addTransaction(transaction);
    }

    @Test
    void depositMoneyTestThrowExceptionIfAmountIsInvalid() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.DEPOSIT, "456", new BigDecimal("-100"), "2021-09-21T10:00:00");
        assertThatThrownBy(() -> service.depositMoney(request))
                .isInstanceOf(AmountFundsException.class)
                .hasMessage("Invalid amount!");
        verifyNoInteractions(accountDAO, transactionDAO, transactionMapper);
    }

    @Test
    void depositMoneyTestThrowExceptionIfAccountNotFound() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.DEPOSIT, "159", new BigDecimal("100"), "2021-09-21T10:00:00");
        when(accountDAO.getAccountByNumber("159")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.depositMoney(request))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account with id '159' not found!");
        verifyNoInteractions(transactionDAO, transactionMapper);
    }

    @Test
    void withdrawMoneyTest() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.WITHDRAWAL, "456", new BigDecimal("100"), "2021-09-21T10:00:00");
        Account account = new Account(5L, "BYN", LocalDate.now(), "65654", new BigDecimal("200"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3"));
        Transaction transaction = new Transaction(1L, TypeTransaction.WITHDRAWAL, account, null, new BigDecimal("100"), LocalDateTime.parse("2021-09-21T10:00:00"));
        when(accountDAO.getAccountByNumber("456")).thenReturn(Optional.of(account));
        when(transactionMapper.buildTransaction(request)).thenReturn(transaction);
        service.withdrawMoney(request);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal("100"));
        verify(accountDAO).updateAccount(account);
        verify(transactionDAO).addTransaction(transaction);
    }

    @Test
    void withdrawMoneyTestThrowExceptionIfAmountIsInvalid() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.WITHDRAWAL, "98754", new BigDecimal("-100"), "2021-10-21T10:00:00");
        assertThatThrownBy(() -> service.withdrawMoney(request))
                .isInstanceOf(AmountFundsException.class)
                .hasMessage("Invalid amount!");
        verifyNoInteractions(accountDAO, transactionDAO, transactionMapper);
    }

    @Test
    void withdrawMoneyTestThrowExceptionIfAccountNotFound() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.WITHDRAWAL, "369852", new BigDecimal("300"), "2021-09-21T10:00:00");
        when(accountDAO.getAccountByNumber("369852")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.withdrawMoney(request))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account with id '369852' not found!");
        verifyNoInteractions(transactionDAO, transactionMapper);
    }

    @Test
    void transferMoneyTest() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.TRANSFER, "65654,951753", new BigDecimal("100"), "2021-09-21T10:00:00");
        Account fromAccount = new Account(5L, "BYN", LocalDate.parse("2021-09-21T10:00:00"), "65654", new BigDecimal("8000"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3"));
        Account toAccount = new Account(3L, "BYN", LocalDate.now(), "951753", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb"));
        Transaction transaction = new Transaction(1L, TypeTransaction.TRANSFER, fromAccount, toAccount, new BigDecimal("100"), LocalDateTime.parse("2021-09-21T10:00:00"));
        when(accountDAO.getAccountByNumber("65654")).thenReturn(Optional.of(fromAccount));
        when(accountDAO.getAccountByNumber("951753")).thenReturn(Optional.of(toAccount));
        when(transactionMapper.buildTransaction(request)).thenReturn(transaction);
        service.transferMoney(request);
        assertThat(fromAccount.getBalance()).isEqualTo(new BigDecimal("7900"));
        assertThat(toAccount.getBalance()).isEqualTo(new BigDecimal("2100"));
        verify(accountDAO).updateAccount(fromAccount);
        verify(accountDAO).updateAccount(toAccount);
        verify(transactionDAO).addTransaction(transaction);
    }

    @Test
    void transferMoneyTestThrowExceptionIfAccountNumberFormatIsInvalid() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.TRANSFER, "789456", new BigDecimal("100"), "2021-09-21T10:00:00");
        assertThatThrownBy(() -> service.transferMoney(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid account number format for transfer!");
        verifyNoInteractions(accountDAO, transactionDAO, transactionMapper);
    }

    @Test
    void transferMoney_should_throw_exception_if_from_account_not_found() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.TRANSFER, "123,456", new BigDecimal("100"), "2018-09-21T10:00:00");
        when(accountDAO.getAccountByNumber("123")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.transferMoney(request))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account with id '123' not found!");
        verifyNoInteractions(transactionDAO, transactionMapper);
    }

    @Test
    void transferMoneyTestThrowExceptionIfToAccountNotFound() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.TRANSFER, "123,456", new BigDecimal("100"), "2019-12-21T10:00:00");
        Account fromAccount = new Account(5L, "BYN", LocalDate.now(), "65654", new BigDecimal("200"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3"));
        when(accountDAO.getAccountByNumber("123")).thenReturn(Optional.of(fromAccount));
        when(accountDAO.getAccountByNumber("456")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.transferMoney(request))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account with id '456' not found!");
        verifyNoInteractions(transactionDAO, transactionMapper);
    }

    @Test
    void transferMoneyTestThrowExceptionIfInsufficientFunds() {
        RequestTransaction request = new RequestTransaction(TypeTransaction.TRANSFER, "65654,951753", new BigDecimal("300"), "2021-09-21T10:00:00");
        Account fromAccount = new Account(5L, "BYN", LocalDate.parse("2021-09-21T10:00:00"), "65654", new BigDecimal("100"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3"));
        Account toAccount = new Account(3L, "BYN", LocalDate.now(), "951753", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb"));
        when(accountDAO.getAccountByNumber("65654")).thenReturn(Optional.of(fromAccount));
        when(accountDAO.getAccountByNumber("951753")).thenReturn(Optional.of(toAccount));
        assertThatThrownBy(() -> service.transferMoney(request))
                .isInstanceOf(AmountFundsException.class)
                .hasMessage("Invalid amount!");
        verifyNoInteractions(transactionDAO, transactionMapper);
    }
}



