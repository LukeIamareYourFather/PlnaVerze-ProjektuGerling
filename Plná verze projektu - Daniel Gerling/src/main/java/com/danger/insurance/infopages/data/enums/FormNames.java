package com.danger.insurance.infopages.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum FormNames {
	
	//Define enums
	PARTY_UPDATE("Aktualizace údajů osoby"),				
	PARTY_CREATE("Evidence nové osoby"),
	PARTY_DELETE("Odstranění osoby z evidence"),
	PARTY_FIND("Vyhledání osoby"),
	POLICY_OWNER_FIND("Vyhledání pojistníka"),
	INSURED_FIND("Vyhledání pojištěnce"),
	NEWS_UPDATE("Aktualizace článku novinek"),				
	INSURANCES_UPDATE("Aktualizace podmínek pojištění"),				
	INSURANCES_CREATE("Evidence nového druhu pojištění"),
	INSURANCES_DELETE("Zrušení existujícího druhu pojištění"),
	INCIDENTS_UPDATE("Aktualizace údajů pojistné události"),
	INCIDENTS_PROCESS("Zpracovávání pojistných událostí"),
	INCIDENTS_CLOSE("Uzavření pojistné události"),
	CONTRACTS_UPDATE("Aktualizace podmínek smlouvy"),
	CONTRACTS_CREATE("Vytvoření nové pojistné smlouvy"),			
	CONTRACTS_FIND("Vyhledání pojistné smlouvy"),
	CONTRACTS_ADD_INSURED("Přidání pojistníka k pojistné smlouvě"),
	ACCOUNT_CREATE("Vytvoření nového uživatele systému"),
	ACCOUNT_FIND("Vyhledání uživatele systému"),
	EMAIL_UPDATE("Aktualizace E-maliu uživatele systému"),
	PASSWORD_UPDATE("Aktualizace hesla uživatele systému"),
	ROLE_UPDATE("Aktualizace uživatelské role v systému");
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	FormNames(String displayName) {
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