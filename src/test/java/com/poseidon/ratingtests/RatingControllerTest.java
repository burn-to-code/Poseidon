package com.poseidon.ratingtests;

import com.poseidon.controllers.RatingController;
import com.poseidon.domain.Rating;
import com.poseidon.services.RatingCrudService;
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
    private RatingCrudService ratingService;

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
        when(ratingService.getAll()).thenReturn(Arrays.asList(rating1, rating2));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService, times(1)).getAll();
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

        verify(ratingService, times(1)).save(any(Rating.class));
        verify(ratingService, times(1)).getAll();
    }

    @Test
    void testShowUpdateForm_success() throws Exception {
        when(ratingService.getById(1)).thenReturn(rating1);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService, times(1)).getById(1); // appelé 2 fois dans le contrôleur
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

        verify(ratingService, times(1)).update(eq(1), any(Rating.class));
    }

    @Test
    void testDeleteRating_success() throws Exception {
        doNothing().when(ratingService).deleteById(1);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteById(1);
    }

    @Test
    void testValidate_BindingResultError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "orderNumber"));

        verify(ratingService, never()).save(any(Rating.class));
    }

    @Test
    void testUpdateRating_BindingResultError() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verify(ratingService, never()).update(anyInt(), any(Rating.class));
    }
}
