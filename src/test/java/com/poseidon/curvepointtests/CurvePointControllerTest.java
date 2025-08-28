package com.poseidon.curvepointtests;

import com.poseidon.controllers.CurveController;
import com.poseidon.domain.CurvePoint;
import com.poseidon.services.interfaces.CrudInterface;
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
    private CrudInterface<CurvePoint> crudService;

    @InjectMocks
    private CurveController curveController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
    }

    @Test
    public void testGetCurvePoints() throws Exception {
        CurvePoint cp = new CurvePoint(10, 15.23, 11.33);
        cp.setId(1);
        when(crudService.getAll()).thenReturn(List.of(cp));

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(crudService, times(1)).getAll();
    }

    @Test
    public void testAddCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void testValidate() throws Exception {
        CurvePoint cp = new CurvePoint(10, 15.23, 11.33);

        when(crudService.save(any(CurvePoint.class))).thenReturn(cp);
        when(crudService.getAll()).thenReturn(List.of(cp));

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "15.23")
                        .param("value", "11.33"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(crudService, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void testGetUpdateCurvePoint() throws Exception {
        CurvePoint cp = new CurvePoint(10, 15.23, 11.33);
        cp.setId(1);
        when(crudService.getById(1)).thenReturn(cp);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));

        verify(crudService, times(1)).getById(1);
    }

    @Test
    public void testPostUpdateCurvePoint() throws Exception {
        CurvePoint cp = new CurvePoint(10, 15.23, 11.33);
        cp.setId(1);

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("id", Objects.toString(cp.getId()))
                        .param("curveId", Objects.toString(cp.getCurveId()))
                        .param("value", Objects.toString(cp.getValue()))
                        .param("term", Objects.toString(cp.getTerm())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(crudService, times(1)).update(eq(cp.getId()), any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        doNothing().when(crudService).deleteById(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(crudService, times(1)).deleteById(1);
    }

    @Test
    public void testValidate_WithBindingErrors_ShouldReturnAddFormAndErrors() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));

        verify(crudService, never()).save(any());
    }

    @Test
    public void testUpdateBid_WithBindingErrors_ShouldReturnUpdateFormAndErrors() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));

        verify(crudService, never()).update(anyInt(), any());
    }
}
