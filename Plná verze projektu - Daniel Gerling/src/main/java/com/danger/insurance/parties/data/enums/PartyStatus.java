package com.danger.insurance.parties.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum PartyStatus {
	
	//Define enums
	UNINSURED("Nepojištěný"),				// Person not yet registered
	REGISTERED("Registrovaný"),				// Registered person without any active insurance contracts
	POLICY_OWNER("Pojistník"),				// Person who owns and holds an active insurance policy
	INSURED("Pojištěnec");					// Person covered by someone else's insurance policy
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
    PartyStatus(String displayName) {
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