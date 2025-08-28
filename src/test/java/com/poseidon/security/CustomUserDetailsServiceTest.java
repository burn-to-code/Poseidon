package com.poseidon.security;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserFound_ShouldReturnUserDetails() {
        // Arrange
        User user = new User();
        user.setUsername("john");
        user.setPassword("pass123");
        user.setRole("USER");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = service.loadUserByUsername("john");

        // Assert
        assertNotNull(userDetails);
        assertEquals("john", userDetails.getUsername());
        assertEquals("pass123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void loadUserByUsername_UserNotFound_ShouldThrowException() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("unknown")
        );

        assertEquals("User Not Found with username: unknown", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknown");
    }
}
