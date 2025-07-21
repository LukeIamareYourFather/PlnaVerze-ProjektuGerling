package com.danger.insurance.accounts.data.enums;

public enum AccountRoles {
	
	//Define enums
	ROLE_PARTY("Osoba"),
	ROLE_EMPLOYEE("Zaměstnanec"),
	ROLE_MANAGER("Manažer"),
	ROLE_JOURNALIST("Novinář"),
	ROLE_ADMINISTRATOR("Administrátor");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	AccountRoles(String displayName) {
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