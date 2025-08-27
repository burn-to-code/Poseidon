package com.poseidon.bidtests;

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.BidListCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidListCrudServiceTest {

    private BidListRepository repository;
    private BidListCrudService service;

    @BeforeEach
    void setUp() {
        repository = mock(BidListRepository.class);
        service = new BidListCrudService(repository);
    }

    // ----------------- getById -----------------
    @Test
    void testGetById_Success() {
        BidList bid = new BidList("acc1", "type1", 10.5);
        bid.setBidListId(1);
        when(repository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = service.getById(1);
        assertEquals(bid, result);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(2));
    }

    @Test
    void testGetById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(-1));
        assertThrows(IllegalArgumentException.class, () -> service.getById(null));
    }

    // ----------------- getAll -----------------
    @Test
    void testGetAll() {
        BidList bid1 = new BidList("acc1", "type1", 10.0);
        BidList bid2 = new BidList("acc2", "type2", 20.0);
        when(repository.findAll()).thenReturn(List.of(bid1, bid2));

        List<BidList> result = service.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(bid1));
        assertTrue(result.contains(bid2));
    }

    // ----------------- save -----------------
    @Test
    void testSave_Success() {
        BidList bid = new BidList("acc1", "type1", 10.0);
        when(repository.save(bid)).thenReturn(bid);

        BidList result = service.save(bid);
        assertEquals(bid, result);
    }

    @Test
    void testSave_WithId_ShouldFail() {
        BidList bid = new BidList("acc1", "type1", 10.0);
        bid.setBidListId(1);
        assertThrows(IllegalArgumentException.class, () -> service.save(bid));
    }

    // ----------------- update -----------------
    @Test
    void testUpdate_Success() {
        BidList existing = new BidList("acc1", "type1", 10.0);
        existing.setBidListId(1);

        BidList updated = new BidList("acc1", "type1", 20.0);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, updated);
        assertEquals(20.0, existing.getBidQuantity());
    }

    @Test
    void testUpdate_InvalidId_ShouldFail() {
        BidList updated = new BidList("acc1", "type1", 20.0);
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
