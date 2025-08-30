package com.poseidon.exceptions;

import com.poseidon.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleAllExceptions() {
        Model model = new BindingAwareModelMap();
        Exception ex = new RuntimeException("Something went wrong");

        String view = handler.handleException(ex, model);

        assertEquals("error", view);
        assertEquals("Une erreur est survenue. Contactez l'administrateur.", model.getAttribute("errorMsg"));
    }
}
