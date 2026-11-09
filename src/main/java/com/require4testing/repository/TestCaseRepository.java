package com.require4testing.repository;

import com.require4testing.model.Requirement;
import com.require4testing.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findByRequirement(Requirement requirement);

    List<TestCase> findByRequirementId(Long requirementId);
}
