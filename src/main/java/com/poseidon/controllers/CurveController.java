package com.poseidon.controllers;

import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForList;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.domain.DTO.GenericMapper;
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

@AllArgsConstructor
@Controller
public class CurveController {

    private final CrudInterface<CurvePoint> service;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {

        model.addAttribute("curvePoints", GenericMapper.mapList(service.getAll(), new CurvePointResponseForList()));

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        service.save(curvePoint);
        model.addAttribute("curvePoints", GenericMapper.mapList(service.getAll(), new CurvePointResponseForList()));

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurvePointResponseForUpdate response = GenericMapper.mapOne(service.getById(id), new CurvePointResponseForUpdate());
        model.addAttribute("curvePoint", response);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        service.update(id, curvePoint);

        model.addAttribute("curvePoints", GenericMapper.mapList(service.getAll(), new CurvePointResponseForList()));

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        service.deleteById(id);

        model.addAttribute("curvePoints", GenericMapper.mapList(service.getAll(), new CurvePointResponseForList()));

        return "redirect:/curvePoint/list";
    }
}
