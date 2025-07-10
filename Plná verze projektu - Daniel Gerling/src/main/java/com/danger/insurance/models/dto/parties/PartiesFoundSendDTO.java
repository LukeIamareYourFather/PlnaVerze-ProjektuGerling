package com.danger.insurance.models.dto.parties;

import java.util.List;

import com.danger.insurance.data.entities.PartiesEntity;

/**
 * Data Transfer Object used to pass a list of parties found based on search criteria
 * from the controller to the view (typically after a successful search operation).
 */
public class PartiesFoundSendDTO {

	// Define lists
	private List<PartiesEntity> foundParties;	// A list of parties (e.g., policy owners or insured individuals) returned from the search operation

	 /**
     * Returns the list of parties found.
     *
     * @return a list of {@link PartiesEntity} instances that match the search criteria
     */
	public List<PartiesEntity> getFoundParties() {
		return foundParties;					
	}

	/**
     * Sets the list of found parties.
     *
     * @param foundParties a list of {@link PartiesEntity} objects retrieved from the database
     */
	public void setFoundParties(List<PartiesEntity> foundParties) {
		this.foundParties = foundParties;
	}
	
}