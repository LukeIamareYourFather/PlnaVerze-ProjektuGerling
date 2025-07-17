package com.danger.insurance.incidents.data.enums;

public enum IncidentResolutionsType {
	
	//Define enums
	APPROVED("Uznáno"),				// Person not yet registered
	REJECTED("Zamítnuto"),				// Registered person without any active insurance contracts
	INVALID("Neplatné"),
	ACTIONLESS("Zaznamenáno");				// Person who owns and holds an active insurance policy
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	IncidentResolutionsType(String displayName) {
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