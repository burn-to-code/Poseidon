package com.poseidon.curvepointtests;


import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurvePointServiceImplTest {

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllForResponseList() {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);

        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint));

        var result = curvePointService.findAllForResponseList();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(15.23, result.get(0).getValue());
    }

    @Test
    public void testSaveCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        var saved = curvePointService.saveCurvePoint(curvePoint);
        assertNotNull(saved.getCreationDate());
        assertEquals(10, saved.getCurveId());
        assertEquals(15.23, saved.getValue());
    }

    @Test
    public void testGetCurrentCurvePointById() {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));

        CurvePointResponseForUpdate result = curvePointService.getUpdateCurvePointById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(15.23, result.getValue());
        assertEquals(11.33, result.getTerm());
    }

    @Test
    public void shouldUpdateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));

        CurvePoint UpdatedCurvePoint = new CurvePoint(10, 17.23, 18.33);

        when(curvePointRepository.save(any(CurvePoint.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CurvePoint response = curvePointService.updateCurvePointById(curvePoint.getId(), UpdatedCurvePoint);

        assertNotNull(response);
        assertEquals(17.23, response.getValue());
        assertEquals(18.33, response.getTerm());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void shouldDeleteCurvePoint() {

        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));

        curvePointService.deleteCurvePointById(1);
        verify(curvePointRepository, times(1)).findById(1);
        verify(curvePointRepository, times(1)).delete(curvePoint);
    }
}
