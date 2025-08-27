package com.poseidon.controllers;

import com.poseidon.domain.Trade;
import com.poseidon.services.TradeCrudService;
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
public class TradeController {

    private final TradeCrudService service;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", service.getAllForList());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "trade/add";
        }

        try {
            service.save(trade);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("trade", service.toDTOForUpdate(id));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }

        try {
            service.update(id, trade);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/trade/list";
    }
}
