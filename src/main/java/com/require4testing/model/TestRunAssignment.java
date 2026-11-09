package com.require4testing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Verknüpfungstabelle (N:M) zwischen TestRun und TestCase, erweitert um
 * den Namen der zugewiesenen Testerin/des zugewiesenen Testers (US-4).
 * Da im Prototyp keine Nutzerverwaltung existiert, wird der Testername
 * als Freitext erfasst.
 */
@Entity
@Table(name = "test_run_assignment")
public class TestRunAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_run_id", nullable = false)
    private TestRun testRun;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @NotBlank(message = "Testername ist Pflichtfeld")
    @Column(name = "tester_name", nullable = false, length = 100)
    private String testerName;

    @OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
    private TestResult result;

    public TestRunAssignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }
}
