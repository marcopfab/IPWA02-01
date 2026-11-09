package com.require4testing.repository;

import com.require4testing.model.TestCase;
import com.require4testing.model.TestRun;
import com.require4testing.model.TestRunAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA leitet die SQL-Abfragen automatisch aus den
 * Methodennamen ab (Query-Derivation) — es ist keine eigene
 * JPQL-Implementierung notwendig.
 */
public interface AssignmentRepository extends JpaRepository<TestRunAssignment, Long> {

    List<TestRunAssignment> findByTestRun(TestRun testRun);

    List<TestRunAssignment> findByTesterName(String testerName);

    long countByTestRun(TestRun testRun); // für Dashboard-Zähler

    // Prüft auf Doppelzuweisung (gleicher Testfall im gleichen Testlauf):
    Optional<TestRunAssignment> findByTestRunAndTestCase(TestRun testRun, TestCase testCase);

    // Prüft beim Löschen eines Testfalls, ob dieser bereits zugewiesen ist:
    List<TestRunAssignment> findByTestCase(TestCase testCase);
}
