package com.poseidon.bidtests;

import com.poseidon.controllers.BidListController;
import com.poseidon.domain.BidList;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BidListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CrudInterface<BidList> crudService;

    @InjectMocks
    private BidListController bidListController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    public void testHome() throws Exception {
        BidList bid = new BidList("Acc", "Type", 10d);
        bid.setBidListId(1);

        when(crudService.getAll()).thenReturn(List.of(bid));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(crudService, times(1)).getAll();
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

        when(crudService.save(any(BidList.class))).thenReturn(bid);
        when(crudService.getAll()).thenReturn(List.of(bid));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(crudService, times(1)).save(any(BidList.class));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        BidList bid = new BidList("Acc", "Type", 10d);
        bid.setBidListId(1);

        when(crudService.getById(1)).thenReturn(bid);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(crudService, times(1)).getById(1);
    }

    @Test
    public void testDeleteBid() throws Exception {
        doNothing().when(crudService).deleteById(1);

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(crudService, times(1)).deleteById(1);
    }
}
