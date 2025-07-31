package com.danger.insurance.shared.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum ActivePageTokens {
	
	//Define enums
	ABOUT_US("about-us"),
	NEWS("news"),
	INSURANCES("insurances"),
	PARTIES("parties"),
	INCIDENTS("incidents"),
	REPORTS("reports"),
	ENROLL("enroll"),
	LOG_IN("log-in"),
	ACCOUNTS("accounts"),
	LOG_OUT("log-out"),
	HOME("");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	ActivePageTokens(String displayName) {
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