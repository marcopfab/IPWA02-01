package com.require4testing.controller;

import com.require4testing.model.TestRun;
import com.require4testing.model.TestRunStatus;
import com.require4testing.service.TestRunService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Web-Schicht für Testläufe und Zuweisungen (US-2, US-4).
 */
@Controller
@RequestMapping("/testruns")
public class TestRunController {

    private final TestRunService service;

    public TestRunController(TestRunService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        var testRuns = service.findAll();
        Map<Long, Long> assignmentCounts = new HashMap<>();
        for (TestRun tr : testRuns) {
            assignmentCounts.put(tr.getId(), service.countAssignments(tr));
        }
        model.addAttribute("testRuns", testRuns);
        model.addAttribute("assignmentCounts", assignmentCounts);
        return "testruns/list"; // -> templates/testruns/list.html
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("testRun", new TestRun());
        model.addAttribute("statuses", TestRunStatus.values());
        return "testruns/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("testRun", service.findById(id));
        model.addAttribute("statuses", TestRunStatus.values());
        return "testruns/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute TestRun testRun, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", TestRunStatus.values());
            return "testruns/form"; // Formular mit Fehlern erneut anzeigen
        }
        service.save(testRun);
        return "redirect:/testruns"; // PRG
    }

    @GetMapping("/{id}/assign")
    public String assignForm(@PathVariable Long id, Model model) {
        TestRun testRun = service.findById(id);
        model.addAttribute("testRun", testRun);
        model.addAttribute("unassignedTestCases", service.findUnassignedTestCases(testRun));
        model.addAttribute("assignments", service.findAssignments(testRun));
        return "testruns/assign"; // -> templates/testruns/assign.html
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable Long id,
                          @RequestParam Long testCaseId,
                          @RequestParam String testerName,
                          Model model) {
        try {
            service.assignTestCase(id, testCaseId, testerName);
        } catch (IllegalStateException ex) {
            // Doppelzuweisung: Fehlermeldung auf der Zuweisungsseite anzeigen
            TestRun testRun = service.findById(id);
            model.addAttribute("testRun", testRun);
            model.addAttribute("unassignedTestCases", service.findUnassignedTestCases(testRun));
            model.addAttribute("assignments", service.findAssignments(testRun));
            model.addAttribute("error", ex.getMessage());
            return "testruns/assign";
        }
        return "redirect:/testruns/" + id + "/assign"; // PRG
    }

    @PostMapping("/{id}/assign/{assignmentId}/delete")
    public String removeAssignment(@PathVariable Long id, @PathVariable Long assignmentId) {
        service.removeAssignment(assignmentId);
        return "redirect:/testruns/" + id + "/assign";
    }
}
