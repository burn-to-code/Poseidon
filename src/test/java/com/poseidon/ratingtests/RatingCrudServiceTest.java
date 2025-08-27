package com.poseidon.ratingtests;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.services.RatingCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingCrudServiceTest {

    @Mock
    RatingRepository repository;

    RatingCrudService service;

    @BeforeEach
    void setUp() {
        service = new RatingCrudService(repository);
    }

    @Test
    void testGetById_Success() {
        Rating rating = new Rating("Aaa", "AA+", "AAA", 1);
        rating.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = service.getById(1);

        assertEquals("Aaa", result.getMoodysRating());
        assertEquals(1, result.getOrderNumber());
        verify(repository).findById(1);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(99));
    }

    @Test
    void testGetAll() {
        Rating r1 = new Rating("Aaa", "AA+", "AAA", 1);
        Rating r2 = new Rating("Baa", "BB+", "BBB", 2);
        when(repository.findAll()).thenReturn(List.of(r1, r2));

        List<Rating> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testSave_Success() {
        Rating rating = new Rating("Aaa", "AA+", "AAA", 1);
        rating.setId(null);
        Rating saved = new Rating("Aaa", "AA+", "AAA", 1);
        saved.setId(1);

        when(repository.save(rating)).thenReturn(saved);

        Rating result = service.save(rating);

        assertEquals(1, result.getId());
    }

    @Test
    void testUpdate_Success() {
        Rating existing = new Rating("Aaa", "AA+", "AAA", 1);
        existing.setId(1);

        Rating update = new Rating("Baa", "BB+", "BBB", 2);
        update.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, update);

        assertEquals("Baa", existing.getMoodysRating());
        assertEquals(2, existing.getOrderNumber());
        verify(repository).save(existing);
    }

    @Test
    void testUpdate_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(null, new Rating("A", "B", "C", 1)));
    }

    @Test
    void testUpdate_NegativeId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(0, new Rating("A", "B", "C", 1)));
    }

    @Test
    void testUpdate_NotFound_ThrowsException() {
        Rating update = new Rating("Baa", "BB+", "BBB", 2);
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.update(99, update));
    }

    @Test
    void testDeleteById_Success() {
        service.deleteById(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testDeleteById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
    }

    @Test
    void testDeleteById_NegativeId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(0));
    }
}
