package com.poseidon.ratingtests;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.services.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        rating1 = new Rating("Moody1", "SP1", "Fitch1", 1);
        rating1.setId(1);

        rating2 = new Rating("Moody2", "SP2", "Fitch2", 2);
        rating2.setId(2);
    }

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<Rating> result = ratingService.findAll();
        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void testSaveRating() {
        when(ratingRepository.save(rating1)).thenReturn(rating1);

        Rating saved = ratingService.saveRating(rating1);
        assertNotNull(saved);
        assertEquals(rating1.getId(), saved.getId());
        verify(ratingRepository, times(1)).save(rating1);
    }

    @Test
    void testFindById_validId() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating1));

        Rating found = ratingService.findById(1);
        assertEquals("Moody1", found.getMoodysRating());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_invalidId() {
        when(ratingRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.findById(99));
        assertTrue(exception.getMessage().contains("Invalid Rating Id"));
        verify(ratingRepository, times(1)).findById(99);
    }

    @Test
    void testUpdateRatingById_validId() {
        when(ratingRepository.save(rating1)).thenReturn(rating1);

        Rating updated = ratingService.updateRatingById(1, rating1);
        assertNotNull(updated);
        assertEquals(rating1.getId(), updated.getId());
        verify(ratingRepository, times(1)).save(rating1);
    }

    @Test
    void testDeleteRatingById() {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.deleteRatingById(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteRatingById_nullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.deleteRatingById(null));
        assertTrue(exception.getMessage().contains("Id Must Not Be Null"));
    }
}
