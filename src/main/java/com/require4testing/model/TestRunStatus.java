package com.require4testing.model;

/**
 * Status eines Testlaufs (TestRun).
 */
public enum TestRunStatus {
    PLANNED("Geplant"),
    IN_PROGRESS("In Bearbeitung"),
    COMPLETED("Abgeschlossen"),
    CANCELLED("Abgebrochen");

    private final String displayName;

    TestRunStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
