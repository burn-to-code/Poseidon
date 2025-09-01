package com.poseidon.controllers;

import com.poseidon.domain.DTO.GenericMapper;
import com.poseidon.domain.DTO.ResponseTradeForList;
import com.poseidon.domain.DTO.ResponseTradeForUpdate;
import com.poseidon.domain.Trade;
import com.poseidon.services.interfaces.CrudInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
public class TradeController {

    private final CrudInterface<Trade> service;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", mapList());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(@ModelAttribute Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/add";
        }

        service.save(trade);

        model.addAttribute("trades", mapList());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("trade", mapOne(id));

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }

        service.update(id, trade);

        model.addAttribute("trades", mapList());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        service.deleteById(id);

        model.addAttribute("trades", mapList());

        return "redirect:/trade/list";
    }

    //helper methods
    private ResponseTradeForUpdate mapOne(Integer id) {
        return GenericMapper.mapOne(service.getById(id), new ResponseTradeForUpdate());
    }

    //helper methods
    private List<ResponseTradeForList> mapList() {
        return GenericMapper.mapList(service.getAll(), new ResponseTradeForList());
    }
}
