package com.require4testing.service;

import com.require4testing.model.Requirement;
import com.require4testing.repository.RequirementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Geschäftslogik rund um Anforderungen (US-1). Einzige Schicht, die
 * direkt auf das RequirementRepository zugreift.
 */
@Service
@Transactional
public class RequirementService {

    private final RequirementRepository requirementRepo;

    // Constructor Injection: testbar ohne Spring-Kontext
    public RequirementService(RequirementRepository requirementRepo) {
        this.requirementRepo = requirementRepo;
    }

    public List<Requirement> findAll() {
        return requirementRepo.findAll();
    }

    public Requirement findById(Long id) {
        return requirementRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requirement not found: " + id));
    }

    public Requirement save(Requirement requirement) {
        return requirementRepo.save(requirement);
    }

    public void delete(Long id) {
        // CascadeType.ALL + orphanRemoval=true auf Requirement.testCases
        // sorgt dafür, dass abhängige Testfälle und deren Zuweisungen
        // automatisch mitgelöscht werden.
        requirementRepo.deleteById(id);
    }
}
