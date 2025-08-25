package com.poseidon.tradetests;

import com.poseidon.domain.DTO.TradeResponseForList;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import com.poseidon.services.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private Trade trade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);
        trade.setSellQuantity(50.0);
    }

    // ----------------- findAll -----------------
    @Test
    void testFindAll_returnsListOfTradeResponseForList() {
        List<Trade> trades = List.of(trade);
        when(tradeRepository.findAll()).thenReturn(trades);

        List<TradeResponseForList> result = tradeService.findAll();

        assertEquals(1, result.size());
        assertEquals(trade.getTradeId(), result.get(0).getId());
        verify(tradeRepository, times(1)).findAll();
    }

    // ----------------- saveTrade -----------------
    @Test
    void testSaveTrade_savesAndReturnsTrade() {
        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade saved = tradeService.saveTrade(trade);

        assertNotNull(saved);
        assertEquals(trade.getAccount(), saved.getAccount());
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testSaveTrade_nullTrade_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> tradeService.saveTrade(null));
        assertEquals("Trade Must Not Be Null", ex.getMessage());
        verifyNoInteractions(tradeRepository);
    }

    // ----------------- getTradeByIdForAddForm -----------------
    @Test
    void testGetTradeByIdForAddForm_returnsTradeResponse() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        TradeResponseForUpdate result = tradeService.getTradeByIdForAddForm(1);

        assertEquals(trade.getTradeId(), result.getId());
        assertEquals(trade.getAccount(), result.getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testGetTradeByIdForAddForm_invalidId_throwsException() {
        when(tradeRepository.findById(2)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tradeService.getTradeByIdForAddForm(2));
        assertEquals("Invalid Trade Id:2", ex.getMessage());
    }

    @Test
    void testGetTradeByIdForAddForm_nullOrNegativeId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> tradeService.getTradeByIdForAddForm(null));
        assertThrows(IllegalArgumentException.class, () -> tradeService.getTradeByIdForAddForm(-1));
    }

    // ----------------- updateTradeById -----------------
    @Test
    void testUpdateTradeById_updatesAndReturnsTrade() {
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("NewAccount");
        updatedTrade.setType("NewType");
        updatedTrade.setBuyQuantity(200.0);
        updatedTrade.setSellQuantity(100.0);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(i -> i.getArgument(0));

        Trade result = tradeService.updateTradeById(1, updatedTrade);

        assertEquals("NewAccount", result.getAccount());
        assertEquals("NewType", result.getType());
        assertEquals(200, result.getBuyQuantity());
        assertEquals(100, result.getSellQuantity());

        verify(tradeRepository, times(1)).findById(1);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testUpdateTradeById_invalidId_throwsException() {
        when(tradeRepository.findById(2)).thenReturn(Optional.empty());
        Trade updatedTrade = new Trade();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> tradeService.updateTradeById(2, updatedTrade));
        assertEquals("Invalid Trade Id:2", ex.getMessage());
    }

    @Test
    void testUpdateTradeById_nullIdOrTrade_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> tradeService.updateTradeById(null, trade));
        assertThrows(IllegalArgumentException.class, () -> tradeService.updateTradeById(1, null));
        assertThrows(IllegalArgumentException.class, () -> tradeService.updateTradeById(-1, trade));
    }

    // ----------------- deleteTradeById -----------------
    @Test
    void testDeleteTradeById_callsRepository() {
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.deleteTradeById(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTradeById_nullOrNegativeId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> tradeService.deleteTradeById(null));
        assertThrows(IllegalArgumentException.class, () -> tradeService.deleteTradeById(-1));
    }
}
