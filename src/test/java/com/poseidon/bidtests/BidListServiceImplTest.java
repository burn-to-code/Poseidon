package com.poseidon.bidtests;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.BidListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidListServiceImplTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBidListForResponseList() {
        BidList bidList = new BidList("account", "type", 10d);
        bidList.setBidListId(1);

        when(bidListRepository.findAll()).thenReturn(List.of(bidList));

        var result = bidListService.getBidListForResponseList();

        assertEquals(1,  result.size());
        assertEquals("account", result.get(0).getAccount());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void testSaveNewBidList() {
        BidList bid = new BidList("Account", "Type", 15d);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);

        BidList saved = bidListService.saveBidList(bid);

        assertNotNull(saved.getCreationDate());
        assertEquals("admin", saved.getCreationName());
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    void testUpdateExistingBidList() {
        BidList existing = new BidList("Old", "Type", 5d);
        existing.setBidListId(1);

        BidListResponseForUpdate updateReq = new BidListResponseForUpdate(1, "New", "NewType", 20d);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(existing));
        when(bidListRepository.save(any(BidList.class))).thenReturn(existing);

        BidList updated = bidListService.updateBidListById(1, updateReq);

        assertEquals("New", updated.getAccount());
        assertEquals(20d, updated.getBidQuantity());
        verify(bidListRepository, times(1)).findById(1);
        verify(bidListRepository, times(1)).save(existing);
    }

    @Test
    void testGetBidListById() {
        BidList bid = new BidList("Account", "Type", 10d);
        bid.setBidListId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidListResponseForUpdate result = bidListService.getBidListByIdForResponse(1);

        assertEquals(1, result.getBidListId());
        assertEquals("Account", result.getAccount());
    }

    @Test
    void testDeleteBidList() {
        BidList bid = new BidList("Account", "Type", 10d);
        bid.setBidListId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        bidListService.deleteBidListById(1);

        verify(bidListRepository, times(1)).findById(1);
        verify(bidListRepository, times(1)).delete(bid);
    }

    @Test
    void testGetBidListByIdForResponse_NullId() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.getBidListByIdForResponse(null)
        );
        assertEquals("Id Must Not Be Null", ex.getMessage());
    }

    @Test
    void testGetBidListByIdForResponse_NegativeId() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.getBidListByIdForResponse(-1)
        );
        assertEquals("Id Must Be Greater Than Zero", ex.getMessage());
    }

    @Test
    void testGetBidListByIdForResponse_InvalidId() {
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.getBidListByIdForResponse(999)
        );
        assertEquals("Invalid bidList Id:999", ex.getMessage());
    }

    @Test
    void testUpdateBidListById_NullId() {
        BidListResponseForUpdate update = new BidListResponseForUpdate(1, "A", "B", 10d);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.updateBidListById(null, update)
        );
        assertEquals("Id Must Not Be Null", ex.getMessage());
    }

    @Test
    void testUpdateBidListById_NegativeId() {
        BidListResponseForUpdate update = new BidListResponseForUpdate(1, "A", "B", 10d);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.updateBidListById(-1, update)
        );
        assertEquals("Id Must Be Greater Than Zero", ex.getMessage());
    }

    @Test
    void testUpdateBidListById_NullBidList() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.updateBidListById(1, null)
        );
        assertEquals("BidList Must Not Be Null", ex.getMessage());
    }

    @Test
    void testUpdateBidListById_InvalidId() {
        BidListResponseForUpdate update = new BidListResponseForUpdate(1, "A", "B", 10d);
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.updateBidListById(999, update)
        );
        assertEquals("Invalid bidList Id:999", ex.getMessage());
    }

    @Test
    void testDeleteBidListById_NullId() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.deleteBidListById(null)
        );
        assertEquals("Id Must Not Be Null", ex.getMessage());
    }

    @Test
    void testDeleteBidListById_NegativeId() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.deleteBidListById(-1)
        );
        assertEquals("Id Must Be Greater Than Zero", ex.getMessage());
    }

    @Test
    void testDeleteBidListById_InvalidId() {
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bidListService.deleteBidListById(999)
        );
        assertEquals("Invalid bidList Id:999", ex.getMessage());
    }

}
