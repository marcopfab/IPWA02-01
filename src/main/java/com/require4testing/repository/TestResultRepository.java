package com.require4testing.repository;

import com.require4testing.model.TestResult;
import com.require4testing.model.TestRunAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    Optional<TestResult> findByAssignment(TestRunAssignment assignment);
}
