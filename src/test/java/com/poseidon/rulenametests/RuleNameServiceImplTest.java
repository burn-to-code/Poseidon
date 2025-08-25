package com.poseidon.rulenametests;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.services.RuleNameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleNameServiceImplTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    private RuleName rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rule = new RuleName();
        rule.setId(1);
        rule.setName("Rule A");
    }

    @Test
    void findAll_ShouldReturnList() {
        when(ruleNameRepository.findAll()).thenReturn(Collections.singletonList(rule));

        List<RuleName> result = ruleNameService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rule A", result.get(0).getName());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void saveRuleName_ShouldSaveRuleName() {
        when(ruleNameRepository.save(rule)).thenReturn(rule);

        RuleName saved = ruleNameService.saveRuleName(rule);

        assertNotNull(saved);
        assertEquals("Rule A", saved.getName());
        verify(ruleNameRepository, times(1)).save(rule);
    }

    @Test
    void saveRuleName_ShouldThrow_WhenNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.saveRuleName(null));
        assertEquals("RuleName Must Not Be Null", ex.getMessage());
        verify(ruleNameRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnRule_WhenExists() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));

        RuleName found = ruleNameService.findById(1);

        assertNotNull(found);
        assertEquals(1, found.getId());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void findById_ShouldThrow_WhenIdNotFound() {
        when(ruleNameRepository.findById(99)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.findById(99));
        assertEquals("Invalid RuleName Id:99", ex.getMessage());
        verify(ruleNameRepository, times(1)).findById(99);
    }

    @Test
    void findById_ShouldThrow_WhenIdIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.findById(null));
        assertEquals("Id Must Not Be Null", ex.getMessage());
        verify(ruleNameRepository, never()).findById(any());
    }

    @Test
    void updateRuleNameById_ShouldUpdate_WhenValid() {
        when(ruleNameRepository.save(rule)).thenReturn(rule);

        RuleName updated = ruleNameService.updateRuleNameById(1, rule);

        assertNotNull(updated);
        assertEquals("Rule A", updated.getName());
        verify(ruleNameRepository, times(1)).save(rule);
    }

    @Test
    void updateRuleNameById_ShouldThrow_WhenIdIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.updateRuleNameById(null, rule));
        assertEquals("Id Must Not Be Null", ex.getMessage());
        verify(ruleNameRepository, never()).save(any());
    }

    @Test
    void updateRuleNameById_ShouldThrow_WhenRuleIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.updateRuleNameById(1, null));
        assertEquals("RuleName Must Not Be Null", ex.getMessage());
        verify(ruleNameRepository, never()).save(any());
    }

    @Test
    void deleteRuleNameById_ShouldDelete_WhenValid() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteRuleNameById(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteRuleNameById_ShouldThrow_WhenIdIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> ruleNameService.deleteRuleNameById(null));
        assertEquals("Id Must Not Be Null", ex.getMessage());
        verify(ruleNameRepository, never()).deleteById(any());
    }
}
