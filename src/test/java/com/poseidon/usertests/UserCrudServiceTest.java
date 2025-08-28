package com.poseidon.usertests;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.services.UserCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCrudServiceTest {

    private UserRepository repository;
    private UserCrudService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserCrudService(repository);
    }

    // ----------------- getById -----------------
    @Test
    void testGetById_Success() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        when(repository.findById(1)).thenReturn(Optional.of(user));

        User result = service.getById(1);
        assertEquals(user, result);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getById(2));
    }

    @Test
    void testGetById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> service.getById(null));
        assertThrows(IllegalArgumentException.class, () -> service.getById(-1));
    }

    // ----------------- getAll -----------------
    @Test
    void testGetAll() {
        User u1 = new User();
        User u2 = new User();
        when(repository.findAll()).thenReturn(List.of(u1, u2));

        List<User> result = service.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));
    }

    // ----------------- save -----------------
    @Test
    void testSave_Success() {
        User user = new User();
        when(repository.save(user)).thenReturn(user);

        User result = service.save(user);
        assertEquals(user, result);
    }

    @Test
    void testSave_WithId_ShouldFail() {
        User user = new User();
        user.setId(1);
        assertThrows(IllegalArgumentException.class, () -> service.save(user));
    }

    // ----------------- update -----------------
    @Test
    void testUpdate_Success() {
        User existing = new User();
        existing.setId(1);
        existing.setUsername("user1");
        existing.setFullname("Full Name");
        existing.setRole("USER");

        User updated = new User();
        updated.setUsername("userUpdated");
        updated.setFullname("New Name");
        updated.setRole("ADMIN");

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        service.update(1, updated);

        assertEquals("userUpdated", existing.getUsername());
        assertEquals("New Name", existing.getFullname());
        assertEquals("ADMIN", existing.getRole());
    }

    @Test
    void testUpdate_InvalidId_ShouldFail() {
        User updated = new User();
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
