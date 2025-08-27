package com.poseidon.curvepointtests;

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.CurvePointCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurvePointCrudServiceTest {

    private CurvePointRepository repository;
    private CurvePointCrudService service;

    @BeforeEach
    void setUp() {
        repository = mock(CurvePointRepository.class);
        service = new CurvePointCrudService(repository);
    }

    // ----------------- getById -----------------
    @Test
    void testGetById_Success() {
        CurvePoint cp = new CurvePoint(10, 15.0, 20.0);
        cp.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(cp));

        CurvePoint result = service.getById(1);
        assertEquals(cp, result);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(2));
    }

    @Test
    void testGetById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(-1));
        assertThrows(IllegalArgumentException.class, () -> service.getById(null));
    }

    // ----------------- getAll -----------------
    @Test
    void testGetAll() {
        CurvePoint cp1 = new CurvePoint(10, 15.0, 20.0);
        CurvePoint cp2 = new CurvePoint(11, 16.0, 21.0);
        when(repository.findAll()).thenReturn(List.of(cp1, cp2));

        List<CurvePoint> result = service.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(cp1));
        assertTrue(result.contains(cp2));
    }

    // ----------------- save -----------------
    @Test
    void testSave_Success() {
        CurvePoint cp = new CurvePoint(10, 15.0, 20.0);
        when(repository.save(cp)).thenReturn(cp);

        CurvePoint result = service.save(cp);
        assertEquals(cp, result);
    }

    @Test
    void testSave_WithId_ShouldFail() {
        CurvePoint cp = new CurvePoint(10, 15.0, 20.0);
        cp.setId(1);
        assertThrows(IllegalArgumentException.class, () -> service.save(cp));
    }

    // ----------------- update -----------------
    @Test
    void testUpdate_Success() {
        CurvePoint existing = new CurvePoint(10, 15.0, 20.0);
        existing.setId(1);

        CurvePoint updated = new CurvePoint(10, 25.0, 20.0);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, updated);
        assertEquals(25.0, existing.getValue());
    }

    @Test
    void testUpdate_InvalidId_ShouldFail() {
        CurvePoint updated = new CurvePoint(10, 25.0, 20.0);
        assertThrows(IllegalArgumentException.class, () -> service.update(null, updated));
        assertThrows(IllegalArgumentException.class, () -> service.update(-1, updated));
    }

    // ----------------- deleteById -----------------
    @Test
    void testDeleteById_Success() {
        doNothing().when(repository).deleteById(1);
        service.deleteById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(-1));
    }
}
