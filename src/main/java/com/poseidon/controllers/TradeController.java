package com.poseidon.controllers;

import com.poseidon.domain.Trade;
import com.poseidon.services.TradeServiceImpl;
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

    private final TradeServiceImpl tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAll());
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
            tradeService.saveTrade(trade);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("trade", tradeService.getTradeByIdForAddForm(id));
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
            tradeService.updateTradeById(id, trade);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            tradeService.deleteTradeById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/trade/list";
    }
}
