package com.require4testing.service;

import com.require4testing.model.Requirement;
import com.require4testing.model.TestCase;
import com.require4testing.repository.AssignmentRepository;
import com.require4testing.repository.TestCaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Geschäftslogik rund um Testfälle (US-3). Testfälle werden stets im
 * Kontext einer Anforderung angelegt.
 */
@Service
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepo;
    private final AssignmentRepository assignmentRepo;

    public TestCaseService(TestCaseRepository testCaseRepo, AssignmentRepository assignmentRepo) {
        this.testCaseRepo = testCaseRepo;
        this.assignmentRepo = assignmentRepo;
    }

    public List<TestCase> findByRequirement(Requirement requirement) {
        return testCaseRepo.findByRequirement(requirement);
    }

    public TestCase findById(Long id) {
        return testCaseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestCase not found: " + id));
    }

    public List<TestCase> findAll() {
        return testCaseRepo.findAll();
    }

    public TestCase save(TestCase testCase) {
        return testCaseRepo.save(testCase);
    }

    public void delete(Long id) {
        TestCase testCase = findById(id);
        // Geschäftsregel: Ein bereits einem Testlauf zugewiesener Testfall
        // darf nicht gelöscht werden, um Dateninkonsistenz zu vermeiden.
        if (!assignmentRepo.findByTestCase(testCase).isEmpty()) {
            throw new IllegalStateException(
                    "Testfall ist bereits einem Testlauf zugewiesen und kann nicht gelöscht werden");
        }
        testCaseRepo.deleteById(id);
    }
}
