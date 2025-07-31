package com.danger.insurance.infopages.data.enums;

/**
 * Represents the status of a party in the insurance system.
 * Can be used to distinguish between contract holders and insured dependents.
 */
public enum FlashMessages {
	
	//Define enums
	INVALID_INPUT("Chybné zadání, prosím opakujte pokus"),
	INSUFFICIENT_INPUT("Nedostačující zadání, prosím zadejte minimální počet parametrů"),
	MISSING_INPUT("Chyba zadání, prosím zadejte alespoň jeden parametr"),
	MISSING_PICTURE("Chyba zadání, prosím nahrajte soubor obrázku"),
	INSUREE_ALREADY_INSURED("Chyba duplicity, pojistník již na vybrané smlouvě existuje"),
	FUTURE_BIRTHDAY("Chyba, narozeniny nesmí být v budoucnosti"),
	FUTURE_DATES("Chyba, datumy nesmí být v budoucnosti"),
	FUTURE_DATE("Chyba, datum nesmí být v budoucnosti"),
	EMAIL_DUPLICATE("Chyba, E-mail je již zaregistrován"),
	PASSWORDS_MISMATCH("Chyba, hesla se spolu neschodují"),
	PARTY_UPDATED("Informace dané osoby byly úspěšně aktualizovány"),
	PARTY_CREATED("Nová osoba byla úspěšně přidána do evidence"),
	PARTY_REMOVED("Osoba byla úspěšně odebrána z evidence"),
	CONTRACT_UPDATED("Podmínky dané smlouvy byly úspěšně aktualizovány"),
	CONTRACT_CREATED("Nová smlouva byla úspěšně vytvořena"),
	CONTRACT_REMOVED("Smlouva byla úspěšně zrušena"),
	CONTRACT_INSURED_ADDED("Pojištěnec byl úspěšně přiřazen ke smlouvě"),
	CONTRACT_INSURED_REMOVED("Pojištěnec byl úspěšně odebrán ze smlouvy"),
	INSURANCE_UPDATED("Podmínky daného typu pojištění byly úspěšně aktualizovány"),
	INSURANCE_CREATED("Nový typ pojištění byl úspěšně přidán"),
	INSURANCE_REMOVED("Typ pojištění byl úspěšně odebrán"),
	INCIDENT_UPDATED("Detaily pojistné události byli úspěšně aktualizovány"),
	INCIDENT_CREATED("Nová pojistná událost byla úspěšně přidána"),
	INCIDENT_REMOVED("Pojistná událost byla úspěšně odebrána"),
	INCIDENT_PROCESSED("Komentář zpracování pojistné události byl úspěšně přidán"),
	NEWS_UPDATED("Novinový článek byl úspěšně aktualizovány"),
	NEWS_CREATED("Nový novinový článek byl úspěšně vytvořen"),
	NEWS_REMOVED("Novinový článek byl úspěšně odebrán"),
	ACCOUNT_CREATED("Nový uživatelský učet byl úspěšně vytvořen"),
	ACCOUNT_REMOVED("Uživatelský účet byl úspěšně zrušen"),
	ACCOUNT_UPDATED("Uživatelský účet byl úspěšně aktualizován");					// Person covered by someone else's insurance policy
	
	// Define Strings
	private final String displayName;		// Stores display name in local language

	/**
     * Constructs the {@code PartyStatus} enum with a display name.
     *
     * @param displayName the localized name for the status.
     */
	FlashMessages(String displayName) {
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