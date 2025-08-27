package com.poseidon.bidtests;

import com.poseidon.controllers.BidListController;
import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.services.BidListCrudService;
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
    private BidListCrudService bidListService;

    @InjectMocks
    private BidListController bidListController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    public void testHome() throws Exception {
        when(bidListService.getAllForList())
                .thenReturn(List.of(new BidListResponseForList(1, "Acc", "Type", 10d)));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService, times(1)).getAllForList();
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

        when(bidListService.save(any(BidList.class))).thenReturn(bid);
        when(bidListService.getAllForList()).thenReturn(List.of());

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).save(any(BidList.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        BidListResponseForUpdate dto = new BidListResponseForUpdate(1, "Acc", "Type", 10d);
        when(bidListService.toDTOForUpdate(1)).thenReturn(dto);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(bidListService, times(1)).toDTOForUpdate(1);
    }

    @Test
    public void testDeleteBid() throws Exception {
        doNothing().when(bidListService).deleteById(1);

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).deleteById(1);
    }
}
