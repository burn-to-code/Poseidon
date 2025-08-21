package com.poseidon.bidtests;

import com.poseidon.controllers.BidListController;
import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.services.BidListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


public class BidListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BidListServiceImpl bidListService;

    @InjectMocks
    private BidListController bidListController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    public void testHome() throws Exception {
        when(bidListService.getBidListForResponseList())
                .thenReturn(List.of(new BidListResponseForList(1, "Acc", "Type", 10d)));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService, times(1)).getBidListForResponseList();
    }

    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    public void testValidate() throws Exception {
        BidList bid = new BidList("Acc", "Type", 10d);

        when(bidListService.saveBidList(any(BidList.class))).thenReturn(bid);
        when(bidListService.getBidListForResponseList()).thenReturn(List.of());

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService, times(1)).saveBidList(any(BidList.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        BidListResponseForUpdate dto = new BidListResponseForUpdate(1, "Acc", "Type", 10d);
        when(bidListService.getBidListByIdForResponse(1)).thenReturn(dto);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(bidListService, times(1)).getBidListByIdForResponse(1);
    }

    @Test
    public void testDeleteBid() throws Exception {
        doNothing().when(bidListService).deleteBidListById(1);
        when(bidListService.getBidListForResponseList()).thenReturn(List.of());

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).deleteBidListById(1);
    }
}
