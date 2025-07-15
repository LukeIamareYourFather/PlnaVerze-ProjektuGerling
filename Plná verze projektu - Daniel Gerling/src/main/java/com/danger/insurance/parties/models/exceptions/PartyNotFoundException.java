package com.danger.insurance.parties.models.exceptions;

/**
 * Exception thrown when a requested party cannot be found in the database.
 * <p>
 * Typically used in service or controller layers when searching by ID or unique fields,
 * and no matching {@code PartiesEntity} is returned.
 * </p>
 *
 * @see com.danger.insurance.parties.models.service.IncidentsServiceImplementation.PartiesServiceImplementation#getById(long)
 * @see com.danger.insurance.parties.data.repositories.PartiesRepository
 */
public class PartyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new {@code PartyNotFoundException} with a default message.
     */
	public PartyNotFoundException() {
        super("Party not found.");
    }
}