package com.require4testing.service;

import com.require4testing.model.TestResult;
import com.require4testing.model.TestResultValue;
import com.require4testing.model.TestRunAssignment;
import com.require4testing.repository.AssignmentRepository;
import com.require4testing.repository.TestResultRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Geschäftslogik rund um die Erfassung von Testergebnissen (US-5).
 */
@Service
@Transactional
public class TestResultService {

    private final TestResultRepository testResultRepo;
    private final AssignmentRepository assignmentRepo;

    public TestResultService(TestResultRepository testResultRepo, AssignmentRepository assignmentRepo) {
        this.testResultRepo = testResultRepo;
        this.assignmentRepo = assignmentRepo;
    }

    public TestRunAssignment findAssignment(Long assignmentId) {
        return assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found: " + assignmentId));
    }

    /**
     * Speichert ein Ergebnis für eine Zuweisung. Existiert bereits ein
     * Ergebnis für diese Zuweisung (geprüft über findByAssignment), wird
     * es überschrieben (Update); andernfalls wird ein neues TestResult
     * angelegt (Insert).
     */
    public TestResult recordResult(Long assignmentId, TestResultValue value, String comment) {
        TestRunAssignment assignment = findAssignment(assignmentId);

        TestResult result = testResultRepo.findByAssignment(assignment)
                .orElseGet(() -> {
                    TestResult r = new TestResult();
                    r.setAssignment(assignment);
                    return r;
                });

        result.setResult(value);
        result.setComment(comment);
        return testResultRepo.save(result);
    }
}
