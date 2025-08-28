package com.poseidon.bidtests;

import com.poseidon.controllers.BidListController;
import com.poseidon.domain.BidList;
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

public class BidListControllerExceptionTest {

    @Mock
    private CrudInterface<BidList> service;

    @InjectMocks
    private BidListController controller;

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
        // Arrange
        when(service.save(any(BidList.class))).thenThrow(new IllegalArgumentException("DB error"));

        // Act & Assert
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Acc")
                        .param("type", "Type")
                        .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // arrange
        when(service.getById(any())).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testUpdateBid_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // arrange
        doThrow(new IllegalArgumentException("DB error")).when(service).update(anyInt(), any(BidList.class));

        mockMvc.perform(post("/bidList/update/1")
                .param("id", "1")
                .param("account", "Acc")
                .param("type", "Type")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testDeleteBid_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }
}
