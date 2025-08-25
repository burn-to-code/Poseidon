package com.poseidon.ratingtests;

import com.poseidon.controllers.RatingController;
import com.poseidon.domain.Rating;
import com.poseidon.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

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
    void testValidate_success() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Moody1")
                        .param("sandPRating", "SP1")
                        .param("fitchRating", "Fitch1")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).saveRating(any(Rating.class));
        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testShowUpdateForm_success() throws Exception {
        when(ratingService.findById(1)).thenReturn(rating1);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService, times(2)).findById(1); // appelé 2 fois dans le contrôleur
    }

    @Test
    void testShowUpdateForm_notFound() throws Exception {
        when(ratingService.findById(99)).thenThrow(new IllegalArgumentException("Invalid Rating Id:99"));

        mockMvc.perform(get("/rating/update/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).findById(99);
    }

    @Test
    void testUpdateRating_success() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", "Moody1")
                        .param("sandPRating", "SP1")
                        .param("fitchRating", "Fitch1")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).updateRatingById(eq(1), any(Rating.class));
    }

    @Test
    void testDeleteRating_success() throws Exception {
        doNothing().when(ratingService).deleteRatingById(1);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteRatingById(1);
    }
}
