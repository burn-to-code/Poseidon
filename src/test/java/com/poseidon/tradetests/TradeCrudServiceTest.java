package com.poseidon.tradetests;

import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import com.poseidon.services.TradeCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeCrudServiceTest {

    private TradeRepository repository;
    private TradeCrudService service;

    @BeforeEach
    void setUp() {
        repository = mock(TradeRepository.class);
        service = new TradeCrudService(repository);
    }

    // ----------------- getById -----------------
    @Test
    void testGetById_Success() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Acc1");
        when(repository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = service.getById(1);
        assertEquals(trade, result);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(2));
    }

    @Test
    void testGetById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(null));
        assertThrows(IllegalArgumentException.class, () -> service.getById(-1));
    }

    // ----------------- getAll -----------------
    @Test
    void testGetAll() {
        Trade t1 = new Trade();
        Trade t2 = new Trade();
        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Trade> result = service.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(t1));
        assertTrue(result.contains(t2));
    }

    // ----------------- save -----------------
    @Test
    void testSave_Success() {
        Trade trade = new Trade();
        when(repository.save(trade)).thenReturn(trade);

        Trade result = service.save(trade);
        assertEquals(trade, result);
    }

    @Test
    void testSave_WithId_ShouldFail() {
        Trade trade = new Trade();
        trade.setTradeId(1);
        assertThrows(IllegalArgumentException.class, () -> service.save(trade));
    }

    // ----------------- update -----------------
    @Test
    void testUpdate_Success() {
        Trade existing = new Trade();
        existing.setTradeId(1);
        existing.setAccount("Acc1");

        Trade updated = new Trade();
        updated.setAccount("AccUpdated");

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, updated);
        assertEquals("AccUpdated", existing.getAccount());
    }

    @Test
    void testUpdate_InvalidId_ShouldFail() {
        Trade updated = new Trade();
        assertThrows(IllegalArgumentException.class, () -> service.update(null, updated));
        assertThrows(IllegalArgumentException.class, () -> service.update(-1, updated));
    }

    // ----------------- deleteById -----------------
    @Test
    void testDeleteById_Success() {
        doNothing().when(repository).deleteById(1);
        service.deleteById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(-1));
    }
}
