package com.poseidon.controllers;

import com.poseidon.domain.DTO.GenericMapper;
import com.poseidon.domain.DTO.TradeResponseForList;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.domain.Trade;
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
public class TradeController {

    private final CrudInterface<Trade> service;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", GenericMapper.mapList(service.getAll(), new TradeResponseForList()));
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser() {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "trade/add";
        }

        service.save(trade);

        model.addAttribute("trades", GenericMapper.mapList(service.getAll(), new TradeResponseForList()));

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("trade", GenericMapper.mapOne(service.getById(id), new TradeResponseForUpdate()));

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }

        service.update(id, trade);

        model.addAttribute("trades", GenericMapper.mapList(service.getAll(), new TradeResponseForList()));

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        service.deleteById(id);

        model.addAttribute("trades", GenericMapper.mapList(service.getAll(), new TradeResponseForList()));

        return "redirect:/trade/list";
    }
}
