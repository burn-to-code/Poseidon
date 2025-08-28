package com.poseidon.ratingtests;

import com.poseidon.controllers.RatingController;
import com.poseidon.domain.Rating;
import com.poseidon.exception.GlobalExceptionHandler;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerExceptionTest {

    @Mock
    private CrudInterface<Rating> service;

    @InjectMocks
    private RatingController controller;

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
        doThrow(new IllegalArgumentException("DB error")).when(service).save(any(Rating.class));

        mockMvc.perform(post("/rating/validate")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        when(service.getById(any())).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testUpdateRating_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).update(anyInt(), any(Rating.class));

        mockMvc.perform(post("/rating/update/1")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }

    @Test
    void testDeleteRating_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", "Une erreur est survenue. Contactez l'administrateur."));
    }
}
