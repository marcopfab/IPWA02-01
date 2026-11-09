package com.require4testing.service;

import com.require4testing.model.TestCase;
import com.require4testing.model.TestRun;
import com.require4testing.model.TestRunAssignment;
import com.require4testing.repository.AssignmentRepository;
import com.require4testing.repository.TestCaseRepository;
import com.require4testing.repository.TestRunRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Geschäftslogik rund um Testläufe und die Zuweisung von Testfällen
 * und Testern (US-2, US-4).
 */
@Service
@Transactional
public class TestRunService {

    private final TestRunRepository testRunRepo;
    private final AssignmentRepository assignmentRepo;
    private final TestCaseRepository testCaseRepo;

    // Constructor Injection: testbar ohne Spring-Kontext
    public TestRunService(TestRunRepository tr, AssignmentRepository ar, TestCaseRepository tc) {
        this.testRunRepo = tr;
        this.assignmentRepo = ar;
        this.testCaseRepo = tc;
    }

    public List<TestRun> findAll() {
        return testRunRepo.findAll();
    }

    public TestRun findById(Long id) {
        return testRunRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestRun not found: " + id));
    }

    public TestRun save(TestRun testRun) {
        return testRunRepo.save(testRun);
    }

    public long countAssignments(TestRun testRun) {
        return assignmentRepo.countByTestRun(testRun);
    }

    public List<TestRunAssignment> findAssignments(TestRun testRun) {
        return assignmentRepo.findByTestRun(testRun);
    }

    /**
     * Liefert alle Testfälle, die diesem Testlauf noch nicht zugewiesen
     * sind (Grundlage für das Dropdown auf der Zuweisungsseite).
     */
    public List<TestCase> findUnassignedTestCases(TestRun testRun) {
        Set<Long> assignedIds = assignmentRepo.findByTestRun(testRun).stream()
                .map(a -> a.getTestCase().getId())
                .collect(Collectors.toSet());
        return testCaseRepo.findAll().stream()
                .filter(tc -> !assignedIds.contains(tc.getId()))
                .collect(Collectors.toList());
    }

    public TestRunAssignment assignTestCase(Long testRunId, Long testCaseId, String testerName) {
        TestRun tr = testRunRepo.findById(testRunId)
                .orElseThrow(() -> new EntityNotFoundException("TestRun not found: " + testRunId));
        TestCase tc = testCaseRepo.findById(testCaseId)
                .orElseThrow(() -> new EntityNotFoundException("TestCase not found: " + testCaseId));

        // Geschäftsregel: Doppelzuweisung verhindern
        if (assignmentRepo.findByTestRunAndTestCase(tr, tc).isPresent()) {
            throw new IllegalStateException("Testfall bereits diesem Testlauf zugewiesen");
        }

        TestRunAssignment a = new TestRunAssignment();
        a.setTestRun(tr);
        a.setTestCase(tc);
        a.setTesterName(testerName);
        return assignmentRepo.save(a);
    }

    /**
     * Entfernt eine Zuweisung, sofern noch kein Ergebnis dafür erfasst wurde.
     */
    public void removeAssignment(Long assignmentId) {
        TestRunAssignment a = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found: " + assignmentId));
        if (a.getResult() != null) {
            throw new IllegalStateException(
                    "Zuweisung hat bereits ein Ergebnis und kann nicht entfernt werden");
        }
        assignmentRepo.deleteById(assignmentId);
    }
}
