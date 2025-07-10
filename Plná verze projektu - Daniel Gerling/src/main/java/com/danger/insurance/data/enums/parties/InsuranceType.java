package com.danger.insurance.data.enums.parties;

/**
 * Represents the type of insurance assigned to a party in the insurance system.
 * Used to distinguish between different levels of coverage or contract complexity.
 */
public enum InsuranceType {
	
	// Define enums
	FREE("Free status"),					// Compulsory health insurance (CHI) only
	FREEPLUS("Free+"),						// (CHI) with one additional insurance contract
	BASIC("Basic"),							// CHI with life insurance and third-party indemnity
	COMFORT("Comfort"),						// Basic insuranceType with one extra contract (e.g. accident or travel)
	GRAND("Grand total");					// Full coverage or high-tier bundle (typically 7+ contracts)
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code InsuranceType} enum with a display name.
     *
     * @param displayName the localized name of the insurance type.
     */
    InsuranceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name associated with this insurance type.
     *
     * @return the localized name of the insurance type.
     */
    public String getDisplayName() {
        return displayName;					// Return insurance type
    }
}