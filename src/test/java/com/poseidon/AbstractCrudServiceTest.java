package com.poseidon;

import com.poseidon.modelfortest.TestEntity;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractCrudServiceTest {

    @Mock
    JpaRepository<TestEntity, Integer> repository;

    AbstractCrudService<TestEntity> service;

    @BeforeEach
    void setUp() {
        service = new AbstractCrudService<>(repository) {};
    }

    // ---------- getById ----------

    @Test
    void testGetById_Success() {
        TestEntity entity = new TestEntity(1, "foo");
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        TestEntity result = service.getById(1);

        assertEquals("foo", result.getName());
        verify(repository).findById(1);
    }

    @Test
    void testGetById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(null));
    }

    @Test
    void testGetById_IdLessThanZero_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(0));
    }

    @Test
    void testGetById_NotFound_ThrowsException() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(99));
    }

    // ---------- getAll ----------

    @Test
    void testGetAll_EmptyList() {
        when(repository.findAll()).thenReturn(List.of());
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    void testGetAll_WithEntities() {
        when(repository.findAll()).thenReturn(List.of(new TestEntity(1, "foo")));
        assertEquals(1, service.getAll().size());
    }

    // ---------- save ----------

    @Test
    void testSave_Success() {
        TestEntity entity = new TestEntity(null, "bar");
        TestEntity savedEntity = new TestEntity(1, "bar");

        when(repository.save(entity)).thenReturn(savedEntity);

        TestEntity result = service.save(entity);

        assertEquals(1, result.getId());
    }

    @Test
    void testSave_NullModel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.save(null));
    }

    @Test
    void testSave_WithId_ThrowsException() {
        TestEntity entity = new TestEntity(1, "bar");
        assertThrows(IllegalArgumentException.class, () -> service.save(entity));
    }

    // ---------- update ----------

    @Test
    void testUpdate_Success() {
        TestEntity existing = spy(new TestEntity(1, "old"));
        TestEntity incoming = new TestEntity(null, "new");

        when(repository.findById(1)).thenReturn(Optional.of(existing));

        service.update(1, incoming);

        verify(existing).update(incoming);  // méthode update appelée
        verify(repository).save(existing);  // save appelé
    }

    @Test
    void testUpdate_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(null, new TestEntity()));
    }

    @Test
    void testUpdate_IdLessThanZero_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(0, new TestEntity()));
    }

    @Test
    void testUpdate_NullModel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.update(1, null));
    }

    @Test
    void testUpdate_NotFound_ThrowsException() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.update(1, new TestEntity()));
    }

    // ---------- deleteById ----------

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
    void testDeleteById_IdLessThanZero_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(0));
    }
}
