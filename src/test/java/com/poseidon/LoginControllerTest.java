package com.poseidon;

import com.poseidon.controllers.LoginController;
import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void login_ShouldReturnLoginView() {
        ModelAndView mav = loginController.login();
        assertEquals("login", mav.getViewName());
        assertTrue(mav.getModel().isEmpty());
    }

    @Test
    void getAllUserArticles_ShouldReturnUserListWithUsers() throws Exception {
        User u1 = new User();
        u1.setUsername("user1");
        User u2 = new User();
        u2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", List.of(u1, u2)));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void error_ShouldReturn403ViewWithErrorMessage() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}
