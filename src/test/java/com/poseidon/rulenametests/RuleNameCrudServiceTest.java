package com.poseidon.rulenametests;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.services.RuleNameCrudService;
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
class RuleNameCrudServiceTest {

    @Mock
    RuleNameRepository repository;

    RuleNameCrudService service;

    @BeforeEach
    void setUp() {
        service = new RuleNameCrudService(repository);
    }

    @Test
    void testGetById_Success() {
        RuleName rule = new RuleName("Name1", "Desc", "Json", "Template", "SQL", "SQLPart");
        rule.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(rule));

        RuleName result = service.getById(1);

        assertEquals("Name1", result.getName());
        assertEquals("Desc", result.getDescription());
        verify(repository).findById(1);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(99));
    }

    @Test
    void testGetAll() {
        RuleName r1 = new RuleName("Name1", "Desc1", "Json1", "Template1", "SQL1", "SQLPart1");
        RuleName r2 = new RuleName("Name2", "Desc2", "Json2", "Template2", "SQL2", "SQLPart2");
        when(repository.findAll()).thenReturn(List.of(r1, r2));

        List<RuleName> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testSave_Success() {
        RuleName rule = new RuleName("Name1", "Desc", "Json", "Template", "SQL", "SQLPart");
        rule.setId(null);
        RuleName saved = new RuleName("Name1", "Desc", "Json", "Template", "SQL", "SQLPart");
        saved.setId(1);

        when(repository.save(rule)).thenReturn(saved);

        RuleName result = service.save(rule);

        assertEquals(1, result.getId());
    }

    @Test
    void testUpdate_Success() {
        RuleName existing = new RuleName("OldName", "OldDesc", "OldJson", "OldTemplate", "OldSQL", "OldSQLPart");
        existing.setId(1);

        RuleName update = new RuleName("NewName", "NewDesc", "NewJson", "NewTemplate", "NewSQL", "NewSQLPart");
        update.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, update);

        assertEquals("NewName", existing.getName());
        assertEquals("NewDesc", existing.getDescription());
        verify(repository).save(existing);
    }

    @Test
    void testUpdate_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(null,
                new RuleName("Name", "Desc", "Json", "Template", "SQL", "SQLPart")));
    }

    @Test
    void testUpdate_NegativeId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(0,
                new RuleName("Name", "Desc", "Json", "Template", "SQL", "SQLPart")));
    }

    @Test
    void testUpdate_NotFound_ThrowsException() {
        RuleName update = new RuleName("NewName", "NewDesc", "NewJson", "NewTemplate", "NewSQL", "NewSQLPart");
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
