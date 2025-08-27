package com.poseidon.controllers;

import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.services.CurvePointCrudService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class CurveController {

    private final CurvePointCrudService service;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        try {
            model.addAttribute("curvePoints", service.getAllForList());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        try {
            service.save(curvePoint);
            model.addAttribute("curvePoints", service.getAllForList());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        try {
            CurvePointResponseForUpdate response = service.toDTOForUpdate(id);
            model.addAttribute("curvePoint", response);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        try {
            service.update(id, curvePoint);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/curvePoint/list";
    }
}
