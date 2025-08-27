package com.poseidon.tradetests;

import com.poseidon.controllers.TradeController;
import com.poseidon.domain.Trade;
import com.poseidon.services.interfaces.CrudInterface;
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
    private CrudInterface<Trade> crudService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void testHome_returnsTradeListView() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);

        when(crudService.getAll()).thenReturn(List.of(trade));

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(crudService, times(1)).getAll();
    }

    @Test
    void testAddTrade_returnsAddView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void testValidate_successRedirects() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);

        when(crudService.save(any(Trade.class))).thenReturn(trade);
        when(crudService.getAll()).thenReturn(List.of(trade));

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(crudService, times(1)).save(any(Trade.class));
    }

    @Test
    void testShowUpdateForm_returnsUpdateView() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);

        when(crudService.getById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));

        verify(crudService, times(1)).getById(1);
    }

    @Test
    void testPostUpdateTrade_successRedirects() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);

        mockMvc.perform(post("/trade/update/1")
                        .param("tradeId", "1")
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("buyQuantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(crudService, times(1)).update(eq(1), any(Trade.class));
    }

    @Test
    void testDeleteTrade_redirectsToList() throws Exception {
        doNothing().when(crudService).deleteById(1);

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(crudService, times(1)).deleteById(1);
    }
}
