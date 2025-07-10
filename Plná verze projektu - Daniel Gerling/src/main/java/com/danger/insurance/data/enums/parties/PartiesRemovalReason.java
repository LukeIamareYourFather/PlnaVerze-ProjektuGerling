package com.danger.insurance.data.enums.parties;

public enum PartiesRemovalReason {

	// Define enums
	RELOCATION("Odstěhování"),
	REDUNDANT("Nadbytečný"),
	REQUESTED("Zažádaný"),
	CEASED("Zaniklý");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	PartiesRemovalReason(String displayName) {
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