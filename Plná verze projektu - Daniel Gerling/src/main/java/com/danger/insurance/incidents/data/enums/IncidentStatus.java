package com.danger.insurance.incidents.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum IncidentStatus {
	
	//Define enums
	OPEN("Nová událost"),				// Person not yet registered
	PROCESSING("Aktuálně se zpracovává"),				// Registered person without any active insurance contracts
	CLOSED("Zpracování dokončeno");				// Person who owns and holds an active insurance policy
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
    IncidentStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name associated with this status.
     *
     * @return the localized name of the party status.
     */
    public String getDisplayName() {
        return displayName;					// Return status name
    }
}