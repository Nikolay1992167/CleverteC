
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;
import ru.clevertec.entity.Bank;
import ru.clevertec.exception.BankNotFoundException;
import ru.clevertec.mapper.BankMapper;
import ru.clevertec.service.BankServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceImplTest {
    @Mock
    private BankDAO bankDAO;
    @Mock
    private BankMapper bankMapper;
    @InjectMocks
    private BankServiceImpl bankService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBanksTest() throws SQLException, IOException, ClassNotFoundException {
        List<Bank> banks = List.of(
                new Bank(1L, "MOBank", "MOZ"),
                new Bank(2L, "ELBank", "ELS")
        );
        List<ResponseBank> expected = List.of(
                new ResponseBank(1L, "MOBank", "MOZ"),
                new ResponseBank(2L, "ELBank", "ELS")
        );
        when(bankDAO.getAllBanks()).thenReturn(banks);
        when(bankMapper.buildBankResponse(any(Bank.class))).thenAnswer(invocation -> {
            Bank bank = invocation.getArgument(0);
            return new ResponseBank(bank.getId(), bank.getTitle(), bank.getBic());
        });
        List<ResponseBank> actual = bankService.getAllBanks();
        assertThat(actual).isEqualTo(expected);
        verify(bankDAO).getAllBanks();
        verify(bankMapper, times(2)).buildBankResponse(any(Bank.class));
    }

    @Test
    void getBankByIdTest() {
        Long id = 1L;
        Bank bank = new Bank(id, "Bank of America", "USA");
        ResponseBank expected = new ResponseBank(id, "Bank of America", "USA");
        when(bankDAO.getBankById(id)).thenReturn(Optional.of(bank));
        when(bankMapper.buildBankResponse(bank)).thenReturn(expected);
        ResponseBank actual = bankService.getBankById(id);
        assertThat(actual).isEqualTo(expected);
        verify(bankDAO).getBankById(id);
        verify(bankMapper).buildBankResponse(bank);
    }

    @Test
    void getBankByIdTestThrowExceptionIfNotExists() {
        Long id = 50L;
        when(bankDAO.getBankById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bankService.getBankById(id))
                .isInstanceOf(BankNotFoundException.class)
                .hasMessage("Bank with id '50' not found!");
        verify(bankDAO).getBankById(id);
        verifyNoInteractions(bankMapper);
    }

    @Test
    void addBankTest() {
        RequestBank requestBank = new RequestBank("WRONG", "WR");
        Bank bank = new Bank(null, "WRONG", "WR");
        when(bankMapper.buildBank(requestBank)).thenReturn(bank);
        bankService.addBank(requestBank);
        verify(bankMapper).buildBank(requestBank);
        verify(bankDAO).addBank(bank);
    }

    @Test
    void updateBankTest() {
        Long id = 1L;
        RequestBank requestBank = new RequestBank("GOOD", "GO");
        Bank bank = new Bank(id, "GOOD", "GO");
        when(bankMapper.buildBank(requestBank)).thenReturn(bank);
        bankService.updateBank(id, requestBank);
        verify(bankMapper).buildBank(requestBank);
        verify(bankDAO).updateBank(bank);
    }

    @Test
    void deleteBankTest() {
        Long id = 1L;
        bankService.deleteBank(id);
        verify(bankDAO).deleteBank(id);
    }
}

