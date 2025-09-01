package com.poseidon.curvepointtests;

import com.poseidon.controllers.CurveController;
import com.poseidon.domain.CurvePoint;
import com.poseidon.exception.GlobalExceptionHandler;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CurvePointControllerExceptionTest {

    @Mock
    private CrudInterface<CurvePoint> service;

    @InjectMocks
    private CurveController controller;

    private MockMvc mockMvc;

    private final String ERROR_MESSAGE = "An error occurred: Please contact Admin or try again later";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testValidate_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // Arrange
        when(service.save(any(CurvePoint.class))).thenThrow(new IllegalArgumentException("DB error"));

        // Act & Assert
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "10.0")
                        .param("value", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // Arrange
        when(service.getById(any())).thenThrow(new IllegalArgumentException("DB error"));

        // Act & Assert
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testUpdate_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("DB error")).when(service).update(anyInt(), any(CurvePoint.class));

        // Act & Assert
        mockMvc.perform(post("/curvePoint/update/1")
                        .param("id", "1")
                        .param("curveId", "1")
                        .param("term", "10.0")
                        .param("value", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testDelete_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        // Act & Assert
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }
}
