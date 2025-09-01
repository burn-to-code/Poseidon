package com.poseidon.rulenametests;

import com.poseidon.controllers.RuleNameController;
import com.poseidon.domain.RuleName;
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

class RuleNameControllerExceptionTest {

    @Mock
    private CrudInterface<RuleName> service;

    @InjectMocks
    private RuleNameController controller;

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
        when(service.save(any(RuleName.class))).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule1")
                        .param("description", "Desc"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        when(service.getById(any())).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testUpdateRuleName_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).update(anyInt(), any(RuleName.class));

        mockMvc.perform(post("/ruleName/update/1")
                        .param("id", "1")
                        .param("name", "Rule1")
                        .param("description", "Desc"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    @Test
    void testDeleteRuleName_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }
}
