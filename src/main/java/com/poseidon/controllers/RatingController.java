package com.poseidon.controllers;

import com.poseidon.domain.Rating;
import com.poseidon.services.interfaces.CrudInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@AllArgsConstructor
public class RatingController {

    private final CrudInterface<Rating> service;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", service.getAll());

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            service.save(rating);
            model.addAttribute("ratings", service.getAll());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("rating", service.getById(id));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/rating/list";
        }

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        try {
            service.update(id, rating);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/rating/list";
    }
}
