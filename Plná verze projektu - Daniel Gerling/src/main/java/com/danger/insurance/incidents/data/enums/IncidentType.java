package com.danger.insurance.incidents.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum IncidentType {
	
	//Define enums
	DAMAGE("Poškození"),				// Person not yet registered
	THEFT("Odcizení"),				// Registered person without any active insurance contracts
	LOST("Ztráta"),				// Person who owns and holds an active insurance policy
	ELEMENTS("Živly"),
	INJURY("Zranění");					// Person covered by someone else's insurance policy
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
    IncidentType(String displayName) {
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