package com.require4testing.controller;

import com.require4testing.model.TestResultValue;
import com.require4testing.service.TestResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web-Schicht für die Erfassung von Testergebnissen (US-5).
 */
@Controller
@RequestMapping("/results")
public class TestResultController {

    private final TestResultService service;

    public TestResultController(TestResultService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String showForm(@RequestParam Long assignmentId, Model model) {
        model.addAttribute("assignment", service.findAssignment(assignmentId));
        model.addAttribute("results", TestResultValue.values());
        return "results/form"; // -> templates/results/form.html
    }

    @PostMapping("/save")
    public String recordResult(@RequestParam Long assignmentId,
                                @RequestParam TestResultValue result,
                                @RequestParam(required = false) String comment) {
        var assignment = service.recordResult(assignmentId, result, comment).getAssignment();
        return "redirect:/testruns/" + assignment.getTestRun().getId() + "/assign"; // PRG
    }
}
