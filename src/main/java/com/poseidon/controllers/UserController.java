package com.poseidon.controllers;

import com.poseidon.domain.DTO.GenericMapper;
import com.poseidon.domain.DTO.ResponseUserDto;
import com.poseidon.domain.User;
import com.poseidon.services.interfaces.CrudInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class UserController {

    private final CrudInterface<User> service;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", GenericMapper.mapList(service.getAll(), new ResponseUserDto()));
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser() {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            service.save(user);
            model.addAttribute("users", service.getAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        ResponseUserDto user = GenericMapper.mapOne(service.getById(id), new ResponseUserDto());
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        service.save(user);
        model.addAttribute("users", GenericMapper.mapList(service.getAll(), new ResponseUserDto()));
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        service.deleteById(id);
        model.addAttribute("users", GenericMapper.mapList(service.getAll(), new ResponseUserDto()));
        return "redirect:/user/list";
    }
}
