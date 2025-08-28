package com.poseidon.controllers;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.domain.DTO.GenericMapper;
import com.poseidon.services.interfaces.CrudInterface;
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

    private final CrudInterface<BidList> service;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidListResponseForList> bidList = GenericMapper.mapList(service.getAll(), new BidListResponseForList());
        model.addAttribute("bidLists", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        service.save(bid);
        model.addAttribute("bidLists", GenericMapper.mapList(service.getAll(), new BidListResponseForList()));

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidListResponseForUpdate bidListUpdateDto = GenericMapper.mapOne(service.getById(id), new BidListResponseForUpdate());
        model.addAttribute("bidList", bidListUpdateDto);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        service.update(id, bidList);

        model.addAttribute("bidLists", GenericMapper.mapList(service.getAll(), new BidListResponseForList()));

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        service.deleteById(id);

        model.addAttribute("bidLists", GenericMapper.mapList(service.getAll(), new BidListResponseForList()));

        return "redirect:/bidList/list";
    }
}
