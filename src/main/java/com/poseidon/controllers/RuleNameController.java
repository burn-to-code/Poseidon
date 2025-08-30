package com.poseidon.controllers;

import com.poseidon.domain.RuleName;
import com.poseidon.services.interfaces.CrudInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class RuleNameController {

    private final CrudInterface<RuleName> service;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", service.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(@ModelAttribute RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/add";
        }

        service.save(ruleName);

        model.addAttribute("ruleNames", service.getAll());

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("ruleName", service.getById(id));

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }

        service.update(id, ruleName);

        model.addAttribute("ruleNames", service.getAll());

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        service.deleteById(id);

        model.addAttribute("ruleNames", service.getAll());

        return "redirect:/ruleName/list";
    }
}
