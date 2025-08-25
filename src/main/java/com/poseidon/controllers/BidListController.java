package com.poseidon.controllers;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.services.BidListServiceImpl;
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

    private final BidListServiceImpl bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        List<BidListResponseForList> bidList = bidListService.getBidListForResponseList();
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

        bidListService.saveBidList(bid);

        model.addAttribute("bidLists", bidListService.getBidListForResponseList());
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListResponseForUpdate bidList;

        try {
            bidList = bidListService.getBidListByIdForResponse(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/bidList/list";
        }

        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListResponseForUpdate bidListDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        try {
            bidListService.updateBidListById(id, bidListDto);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidListService.deleteBidListById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/bidList/list";
    }
}
