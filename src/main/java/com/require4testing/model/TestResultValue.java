package com.require4testing.model;

/**
 * Mögliche Ergebniswerte eines ausgeführten Testfalls.
 */
public enum TestResultValue {
    PASSED("Bestanden"),
    FAILED("Fehlgeschlagen"),
    BLOCKED("Blockiert"),
    NOT_EXECUTED("Nicht ausgeführt");

    private final String displayName;

    TestResultValue(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
