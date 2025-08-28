package com.poseidon.tradetests;

import com.poseidon.controllers.TradeController;
import com.poseidon.domain.Trade;
import com.poseidon.exception.GlobalExceptionHandler;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TradeControllerExceptionTest {

    @Mock
    private CrudInterface<Trade> service;

    @InjectMocks
    private TradeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testValidate_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).save(any(Trade.class));

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("buyQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        when(service.getById(anyInt())).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testUpdateTrade_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).update(anyInt(), any(Trade.class));

        mockMvc.perform(post("/trade/update/1")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("buyQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testDeleteTrade_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }
}
