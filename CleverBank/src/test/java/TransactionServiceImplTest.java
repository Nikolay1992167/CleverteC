import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.data.transaction.response.ResponseTransaction;
import ru.clevertec.entity.*;
import ru.clevertec.exception.TransactionNotFoundException;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.service.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTransactionsTest() {
        List<Transaction> transactions = List.of(
                new Transaction(1L, TypeTransaction.DEPOSIT, null, new Account(5L, "BYN", LocalDate.now(), "65654", new BigDecimal("100"), new Bank(1L, "VTB", "BTV"), new User(3L, "Igor Popov", "popov@mail.com", "popov3")), new BigDecimal("100"), LocalDateTime.now()),
                new Transaction(2L, TypeTransaction.WITHDRAWAL, new Account(3L, "BYN", LocalDate.now(), "951753", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb")), null, new BigDecimal("200"), LocalDateTime.now())
        );
        List<ResponseTransaction> responseTransactions = List.of(
                new ResponseTransaction(1L, TypeTransaction.DEPOSIT, null, "65654", new BigDecimal("100"), String.valueOf(LocalDateTime.now())),
                new ResponseTransaction(2L, TypeTransaction.WITHDRAWAL, "951753", null, new BigDecimal("100"), String.valueOf(LocalDateTime.now())
                ));
        when(transactionDAO.getAllTransactions()).thenReturn(transactions);
        when(transactionMapper.buildTransactionResponse(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            return responseTransactions.stream()
                    .filter(response -> response.getId().equals(transaction.getId()))
                    .findFirst()
                    .orElse(null);
        });
        List<ResponseTransaction> actual = service.getAllTransactions();
        assertThat(actual).isEqualTo(responseTransactions);
        verify(transactionDAO).getAllTransactions();
        verify(transactionMapper, times(2)).buildTransactionResponse(any(Transaction.class));
    }

    @Test
    void getTransactionByIdTest() {
        Long id = 1L;
        Transaction transaction = new Transaction(id, TypeTransaction.DEPOSIT, null, new Account(3L, "BYN", LocalDate.now(), "951753", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb")), new BigDecimal("100"), LocalDateTime.now());
        ResponseTransaction responseTransaction = new ResponseTransaction(id, TypeTransaction.DEPOSIT, null, "951753", new BigDecimal("2000"), String.valueOf(LocalDateTime.now()));
        when(transactionDAO.getTransactionById(id)).thenReturn(Optional.of(transaction));
        when(transactionMapper.buildTransactionResponse(transaction)).thenReturn(responseTransaction);
        ResponseTransaction actual = service.getTransactionById(id);
        assertThat(actual).isEqualTo(responseTransaction);
        verify(transactionDAO).getTransactionById(id);
        verify(transactionMapper).buildTransactionResponse(transaction);
    }

    @Test
    void getTransactionByIdTestThrowExceptionIfNotFound() {
        Long id = 1L;
        when(transactionDAO.getTransactionById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getTransactionById(id))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transaction with id '1' not found");
        verify(transactionDAO).getTransactionById(id);
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void addTransactionTest() {
        RequestTransaction requestTransaction = new RequestTransaction(TypeTransaction.DEPOSIT, "951753", new BigDecimal("100"), String.valueOf(LocalDateTime.now()));
        Transaction transaction = new Transaction(null, TypeTransaction.DEPOSIT, null, new Account(3L, "BYN", LocalDate.now(), "951753", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb")), new BigDecimal("100"), LocalDateTime.now());
        when(transactionMapper.buildTransaction(requestTransaction)).thenReturn(transaction);
        service.addTransaction(requestTransaction);
        verify(transactionMapper).buildTransaction(requestTransaction);
        verify(transactionDAO).addTransaction(transaction);
    }

    @Test
    void updateTransactionTest() {
        Long id = 1L;
        RequestTransaction requestTransaction = new RequestTransaction(TypeTransaction.WITHDRAWAL, "456", new BigDecimal("200"), String.valueOf(LocalDateTime.now()));
        Transaction transaction = new Transaction(id, TypeTransaction.WITHDRAWAL,new Account(3L, "BYN", LocalDate.now(), "456", new BigDecimal("2000"), new Bank(1L, "VTB", "BTV"), new User(9L, "Vasja Blinov", "blinov@mail.com", "vonilb")), null, new BigDecimal("200"), LocalDateTime.now());
        when(transactionMapper.buildTransaction(requestTransaction)).thenReturn(transaction);
        service.updateTransaction(id, requestTransaction);
        verify(transactionMapper).buildTransaction(requestTransaction);
        verify(transactionDAO).updateTransaction(transaction);
    }

    @Test
    void deleteTransactionTest() {
        Long id = 1L;
        service.deleteTransaction(id);
        verify(transactionDAO).deleteTransaction(id);
    }
}

