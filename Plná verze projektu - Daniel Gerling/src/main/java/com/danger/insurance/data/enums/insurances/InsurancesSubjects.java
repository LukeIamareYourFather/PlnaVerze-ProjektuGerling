package com.danger.insurance.data.enums.insurances;

public enum InsurancesSubjects {

	//Define enums
	PERSON("Osoba"),
	PROPERTY("Nemovitost"),
	ASSETS("Majetek"),
	LEGAL("Právní subjekt"),
	ANIMAL("Zvíře");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	InsurancesSubjects(String displayName) {
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