package com.poseidon.curvepointtests;

import com.poseidon.controllers.CurveController;
import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForList;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.services.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CurvePointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurvePointServiceImpl curvePointService;

    @InjectMocks
    private CurveController curve;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(curve).build();
    }

    @Test
    public void testGetCurvePoints() throws Exception {
        CurvePointResponseForList expectedPoint = new CurvePointResponseForList(1, 10, 15.23, 11.33);
        when(curvePointService.findAllForResponseList()).thenReturn(List.of(expectedPoint));

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andReturn();

        verify(curvePointService, times(1)).findAllForResponseList();
    }

    @Test
    public void testAddCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    public void testValidate() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("term", "15.23")
                        .param("value", "11.33"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void testGetUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);
        curvePoint.setId(1);

        when(curvePointService.getUpdateCurvePointById(1)).thenReturn(new CurvePointResponseForUpdate(curvePoint.getCurveId(), curvePoint.getValue(), curvePoint.getTerm()));

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));

        verify(curvePointService, times(1)).getUpdateCurvePointById(1);
    }

    @Test
    public void testPostUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(10, 15.23, 11.33);
        curvePoint.setId(1);

        when(curvePointService.updateCurvePointById(curvePoint.getId(), curvePoint)).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("id", Objects.toString(curvePoint.getId()))
                        .param("curveId", Objects.toString(curvePoint.getCurveId()))
                        .param("value", Objects.toString(curvePoint.getValue()))
                        .param("term", Objects.toString(curvePoint.getTerm())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1))
                .updateCurvePointById(eq(curvePoint.getId()), any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        doNothing().when(curvePointService).deleteCurvePointById(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteCurvePointById(1);
    }




}
