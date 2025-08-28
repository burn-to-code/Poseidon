package com.poseidon.usertests;

import com.poseidon.controllers.UserController;
import com.poseidon.domain.User;
import com.poseidon.services.interfaces.CrudInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private CrudInterface<User> service;

    @InjectMocks
    private UserController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ------------------ /user/list ------------------
    @Test
    void testHome_ShouldReturnUserListView() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        when(service.getAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

        verify(service, times(1)).getAll();
    }

    // ------------------ /user/add GET ------------------
    @Test
    void testAddUserForm_ShouldReturnAddView() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    // ------------------ /user/validate POST ------------------
    @Test
    void testValidate_ShouldSaveUserAndRedirect() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass123");
        when(service.getAll()).thenReturn(List.of(user));
        when(service.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/validate")
                        .param("username", "user1")
                        .param("password", "pass123")
                        .param("role", "USER")
                        .param("fullname", "Full Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service, times(1)).save(any(User.class));
    }

    // ------------------ /user/update/{id} GET ------------------
    @Test
    void testShowUpdateForm_ShouldReturnUpdateView() throws Exception {
        User user = new User();
        user.setId(1);
        when(service.getById(1)).thenReturn(user);

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));

        verify(service, times(1)).getById(1);
    }

    // ------------------ /user/update/{id} POST ------------------
    @Test
    void testUpdateUser_ShouldSaveAndRedirect() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setPassword("pass123");
        when(service.getAll()).thenReturn(List.of(user));

        mockMvc.perform(post("/user/update/1")
                        .param("username", "user1")
                        .param("password", "pass123")
                        .param("role", "USER")
                        .param("fullname", "Full Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service, times(1)).save(any(User.class));
    }

    // ------------------ /user/delete/{id} ------------------
    @Test
    void testDeleteUser_ShouldCallServiceAndRedirect() throws Exception {
        User user = new User();
        user.setId(1);
        when(service.getAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(service, times(1)).deleteById(1);
    }

    @Test
    public void testValidateUserBindingResultError() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("password", "secret")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "username"));

        verify(service, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUserBindingResultError() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("existingUser");
        user.setPassword("pass");
        user.setFullname("Full Name");
        user.setRole("USER");

        when(service.getById(1)).thenReturn(user);

        mockMvc.perform(post("/user/update/1")
                        .param("username", "existingUser")
                        .param("password", "")
                        .param("fullname", "John Doe")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("user", "password"));

        verify(service, never()).save(any(User.class));
    }

}
