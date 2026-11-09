package com.require4testing.model;

/**
 * Status einer Anforderung (Requirement).
 */
public enum RequirementStatus {
    OPEN("Offen"),
    IN_REVIEW("In Prüfung"),
    APPROVED("Freigegeben"),
    CLOSED("Geschlossen");

    private final String displayName;

    RequirementStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
