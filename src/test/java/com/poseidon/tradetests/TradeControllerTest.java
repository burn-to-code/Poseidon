package com.poseidon.tradetests;

import com.poseidon.controllers.TradeController;
import com.poseidon.domain.Trade;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.services.TradeCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TradeControllerTest {

    @Mock
    private TradeCrudService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);
        trade.setSellQuantity(50.0);
    }

    // ----------------- /trade/list -----------------
    @Test
    void testHome_returnsTradeListView() throws Exception {
        when(tradeService.getAllForList()).thenReturn(List.of());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(tradeService, times(1)).getAllForList();
    }

    // ----------------- /trade/add -----------------
    @Test
    void testAddUser_returnsAddView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    // ----------------- /trade/validate -----------------
    @Test
    void testValidate_successRedirects() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).save(any(Trade.class));
    }

    @Test
    void testValidate_bindingErrors_returnsListView() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "Type")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    // ----------------- /trade/update/{id} GET -----------------
    @Test
    void testShowUpdateForm_returnsUpdateView() throws Exception {
        TradeResponseForUpdate response = new TradeResponseForUpdate(1, "Acc", "Type", 100.0);
        when(tradeService.toDTOForUpdate(1)).thenReturn(response);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));

        verify(tradeService, times(1)).toDTOForUpdate(1);
    }

    @Test
    void testShowUpdateForm_invalidId_redirects() throws Exception {
        when(tradeService.toDTOForUpdate(2)).thenThrow(new IllegalArgumentException("Invalid Trade Id:2"));

        mockMvc.perform(get("/trade/update/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).toDTOForUpdate(2);
    }

    // ----------------- /trade/update/{id} POST -----------------
    @Test
    void testUpdateTrade_successRedirects() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).update(eq(1), any(Trade.class));
    }

    @Test
    void testUpdateTrade_bindingErrors_returnsListView() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", "Type")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    // ----------------- /trade/delete/{id} -----------------
    @Test
    void testDeleteTrade_redirectsToList() throws Exception {
        doNothing().when(tradeService).deleteById(1);

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).deleteById(1);
    }
}
