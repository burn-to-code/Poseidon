package com.poseidon.controllers;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.services.BidListCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BidListController {

    private final BidListCrudService service;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidListResponseForList> bidList = service.getAllForList();
        model.addAttribute("bidLists", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        try {
            service.save(bid);
            model.addAttribute("bidLists", service.getAllForList());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "bidList/add";
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        try {
            BidListResponseForUpdate bidListUpdateDto = service.toDTOForUpdate(id);
            model.addAttribute("bidList", bidListUpdateDto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/bidList/list";
        }


        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        try {
            service.update(id, bidList);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/bidList/list";
    }
}
