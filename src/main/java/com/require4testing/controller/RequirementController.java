package com.require4testing.controller;

import com.require4testing.model.Requirement;
import com.require4testing.model.RequirementStatus;
import com.require4testing.service.RequirementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Web-Schicht für Anforderungen (US-1). @Controller statt @RestController,
 * da HTML über Thymeleaf gerendert wird (kein JSON-REST).
 */
@Controller
@RequestMapping("/requirements")
public class RequirementController {

    private final RequirementService service;

    public RequirementController(RequirementService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requirements", service.findAll());
        return "requirements/list"; // -> templates/requirements/list.html
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("requirement", new Requirement());
        model.addAttribute("statuses", RequirementStatus.values());
        return "requirements/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("requirement", service.findById(id));
        model.addAttribute("statuses", RequirementStatus.values());
        return "requirements/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Requirement requirement, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", RequirementStatus.values());
            return "requirements/form"; // Formular mit Fehlern erneut anzeigen
        }
        service.save(requirement);
        return "redirect:/requirements"; // PRG: verhindert Doppel-POST
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/requirements";
    }
}
