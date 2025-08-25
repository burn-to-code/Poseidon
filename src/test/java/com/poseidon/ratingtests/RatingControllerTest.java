package com.poseidon.controllers;

import com.poseidon.domain.Rating;
import com.poseidon.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();

        rating1 = new Rating("Moody1", "SP1", "Fitch1", 1);
        rating1.setId(1);

        rating2 = new Rating("Moody2", "SP2", "Fitch2", 2);
        rating2.setId(2);
    }

    @Test
    void testHome() throws Exception {
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void testValidate_withErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = ratingController.validate(rating1, bindingResult, model);
        assertEquals("rating/add", view);
        verify(ratingService, never()).saveRating(any());
    }

    @Test
    void testValidate_noErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        String view = ratingController.validate(rating1, bindingResult, model);
        assertEquals("rating/add", view);
        verify(ratingService, times(1)).saveRating(rating1);
        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testShowUpdateForm_validId() throws Exception {
        when(ratingService.findById(1)).thenReturn(rating1);

        String view = ratingController.showUpdateForm(1, model);
        assertEquals("rating/update", view);
        verify(ratingService, times(2)).findById(1);
    }

    @Test
    void testShowUpdateForm_invalidId() throws Exception {
        when(ratingService.findById(99)).thenThrow(new IllegalArgumentException("Invalid Rating Id:99"));

        String view = ratingController.showUpdateForm(99, model);
        assertEquals("redirect:/rating/list", view);
        verify(ratingService, times(1)).findById(99);
        verify(model, times(1)).addAttribute(eq("error"), anyString());
    }

    @Test
    void testUpdateRating_withErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = ratingController.updateRating(1, rating1, bindingResult, model);
        assertEquals("rating/update", view);
        verify(ratingService, never()).updateRatingById(anyInt(), any());
    }

    @Test
    void testUpdateRating_noErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(ratingService.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        String view = ratingController.updateRating(1, rating1, bindingResult, model);
        assertEquals("redirect:/rating/list", view);
        verify(ratingService, times(1)).updateRatingById(1, rating1);
        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testDeleteRating() throws Exception {
        doNothing().when(ratingService).deleteRatingById(1);

        String view = ratingController.deleteRating(1, model);
        assertEquals("redirect:/rating/list", view);
        verify(ratingService, times(1)).deleteRatingById(1);
    }
}
