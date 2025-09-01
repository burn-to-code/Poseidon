package com.poseidon.usertests;

import com.poseidon.controllers.UserController;
import com.poseidon.domain.User;
import com.poseidon.exception.GlobalExceptionHandler;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerExceptionTest {

    @Mock
    private CrudInterface<User> service;

    @InjectMocks
    private UserController controller;

    private MockMvc mockMvc;

    private final String ERROR_MESSAGE = "An error occurred: Please contact Admin or try again later";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ------------------ /user/validate ------------------
    @Test
    void testValidate_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).save(any(User.class));

        mockMvc.perform(post("/user/validate")
                        .param("username", "user1")
                        .param("password", "Pass123!")
                        .param("fullname", "Full Name")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    // ------------------ /user/update/{id} GET ------------------
    @Test
    void testShowUpdateForm_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        when(service.getById(any())).thenThrow(new IllegalArgumentException("DB error"));

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    // ------------------ /user/update/{id} POST ------------------
    @Test
    void testUpdateUser_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).update(any(), any(User.class));

        mockMvc.perform(post("/user/update/1")
                        .param("username", "user1")
                        .param("password", "Pass123!")
                        .param("fullname", "Full Name")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }

    // ------------------ /user/delete/{id} ------------------
    @Test
    void testDeleteUser_WhenServiceThrowsException_ShouldRenderErrorPage() throws Exception {
        doThrow(new IllegalArgumentException("DB error")).when(service).deleteById(anyInt());

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMsg", ERROR_MESSAGE));
    }
}
