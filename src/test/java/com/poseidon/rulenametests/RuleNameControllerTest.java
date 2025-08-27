package com.poseidon.rulenametests;

import com.poseidon.controllers.RuleNameController;
import com.poseidon.domain.RuleName;
import com.poseidon.services.RuleNameCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RuleNameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RuleNameCrudService ruleNameService;

    @InjectMocks
    private RuleNameController ruleNameController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();
    }

    @Test
    void home_shouldReturnListViewWithRuleNames() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of(new RuleName()));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService, times(1)).getAll();
    }

    @Test
    void addRuleForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validate_withValidRuleName_shouldRedirectToList() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of(new RuleName()));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleName.class));
    }

    @Test
    void validate_withValidationErrors_shouldReturnAddView() throws Exception {
        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    void showUpdateForm_withValidId_shouldReturnUpdateView() throws Exception {
        when(ruleNameService.getById(1)).thenReturn(new RuleName());

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));

        verify(ruleNameService, times(1)).getById(1);
    }

    @Test
    void updateRuleName_withValidData_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "Updated Rule"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).update(eq(1), any(RuleName.class));
    }

    @Test
    void updateRuleName_withValidationErrors_shouldReturnUpdateView() throws Exception {
        mockMvc.perform(post("/ruleName/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    void deleteRuleName_shouldRedirectToList() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).deleteById(1);
    }
}
