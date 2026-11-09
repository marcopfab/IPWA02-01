package com.require4testing.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Das Ergebnis eines zugewiesenen Testfalls (US-5). 1:1-Beziehung zu
 * TestRunAssignment über den eindeutigen Fremdschlüssel assignment_id.
 */
@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "assignment_id", nullable = false, unique = true)
    private TestRunAssignment assignment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestResultValue result;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @CreationTimestamp
    @Column(name = "executed_at", updatable = false)
    private LocalDateTime executedAt;

    public TestResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestRunAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(TestRunAssignment assignment) {
        this.assignment = assignment;
    }

    public TestResultValue getResult() {
        return result;
    }

    public void setResult(TestResultValue result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
