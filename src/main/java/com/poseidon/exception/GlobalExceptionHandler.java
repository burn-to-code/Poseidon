package com.poseidon.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String message = "An error occurred: Please contact Admin or try again later";

        log.error(message, ex);

        model.addAttribute("errorMsg", message);
        return "error";
    }
}
