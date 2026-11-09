package com.require4testing.controller;

import com.require4testing.model.Requirement;
import com.require4testing.model.TestCase;
import com.require4testing.service.RequirementService;
import com.require4testing.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Web-Schicht für Testfälle (US-3). Testfälle werden stets im Kontext
 * einer Anforderung angezeigt bzw. angelegt, daher der Query-Parameter
 * reqId auf allen GET-Routen.
 */
@Controller
@RequestMapping("/testcases")
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final RequirementService requirementService;

    public TestCaseController(TestCaseService testCaseService, RequirementService requirementService) {
        this.testCaseService = testCaseService;
        this.requirementService = requirementService;
    }

    /**
     * Registriert einen PropertyEditor, der das versteckte Formularfeld
     * "requirement" (nur die ID als Text) auf die zugehörige
     * Requirement-Entity abbildet, damit Spring die ManyToOne-Beziehung
     * aus dem HTML-Formular korrekt binden kann.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Requirement.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue((text == null || text.isBlank()) ? null : requirementService.findById(Long.parseLong(text)));
            }

            @Override
            public String getAsText() {
                Requirement r = (Requirement) getValue();
                return r == null ? "" : String.valueOf(r.getId());
            }
        });
    }

    @GetMapping
    public String list(@RequestParam Long reqId, Model model) {
        Requirement requirement = requirementService.findById(reqId);
        model.addAttribute("requirement", requirement);
        model.addAttribute("testCases", testCaseService.findByRequirement(requirement));
        return "testcases/list"; // -> templates/testcases/list.html
    }

    @GetMapping("/new")
    public String showForm(@RequestParam Long reqId, Model model) {
        Requirement requirement = requirementService.findById(reqId);
        TestCase testCase = new TestCase();
        testCase.setRequirement(requirement);
        model.addAttribute("testCase", testCase);
        model.addAttribute("requirement", requirement);
        return "testcases/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute TestCase testCase, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("requirement", testCase.getRequirement());
            return "testcases/form"; // Formular mit Fehlern erneut anzeigen
        }
        testCaseService.save(testCase);
        return "redirect:/testcases?reqId=" + testCase.getRequirement().getId(); // PRG
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam Long reqId) {
        try {
            testCaseService.delete(id);
        } catch (IllegalStateException ex) {
            // Löschung wurde durch die Geschäftsregel in TestCaseService
            // blockiert (Testfall bereits einem Testlauf zugewiesen).
            String encoded = URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/testcases?reqId=" + reqId + "&error=" + encoded;
        }
        return "redirect:/testcases?reqId=" + reqId;
    }
}
