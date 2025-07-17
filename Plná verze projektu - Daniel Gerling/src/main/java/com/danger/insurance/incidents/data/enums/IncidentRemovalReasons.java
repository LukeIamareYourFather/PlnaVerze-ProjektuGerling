package com.danger.insurance.incidents.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum IncidentRemovalReasons {
	
	//Define enums
	DUPLICATE("Duplicita"),				// Person not yet registered
	MISTAKE("Chyba"),				// Registered person without any active insurance contracts
	OUTDATED("Zastaralý"),				// Person who owns and holds an active insurance policy
	MERGED("Sjednocení"),
	TESTING("Testování");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
    IncidentRemovalReasons(String displayName) {
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