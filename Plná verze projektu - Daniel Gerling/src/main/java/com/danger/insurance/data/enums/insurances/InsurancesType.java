package com.danger.insurance.data.enums.insurances;

public enum InsurancesType {

	//Define enums
	HEALTH("Zdravotní pojištění"),				// Person not yet registered
	LIFE("Životní pojištění"),				// Registered person without any active insurance contracts
	DISABILITY("Úrazové pojištění"),				// Person who owns and holds an active insurance policy
	VEHICLE("Pojištění vozidel"),					// Person covered by someone else's insurance policy
	PROPERTY("Pojištění majetku"),
	NATURAL_DISASTERS("Pojištění proti živlům"),
	TRAVEL("Cestovní pojištění"),
	ANIMAL("Pojištění zvířat"),
	LEGAL("Právní pojištění");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	InsurancesType(String displayName) {
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