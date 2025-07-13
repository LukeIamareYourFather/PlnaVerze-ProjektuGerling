package com.danger.insurance.parties.data.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface PartiesRepository extends CrudRepository<PartiesEntity, Long> {
	
	// Spring Data automatically implements these based on method names
	Optional<PartiesEntity> findByName(String name);
	Optional<PartiesEntity> findBySurname(String surname);
	Optional<PartiesEntity> findByEmail(String email);
	Optional<PartiesEntity> findByPhoneNumber(String phoneNumber);
	Optional<PartiesEntity> findByCity(String city);
	Optional<PartiesEntity> findByStreet(String street);
	Optional<PartiesEntity> findByZipCode(String zipCode);
	Optional<PartiesEntity> findByBirthNumber(String birthNumber);
	Optional<PartiesEntity> findByBirthDay(LocalDate birthDay);
	
	/**
     * Custom query to search for parties matching a specific status and optional personal attributes.
     *
     * @param partyStatus the party status to match (e.g., {@code POLICY_OWNER}, {@code INSURED})
     * @param name optional first name
     * @param surname optional surname
     * @param street optional street
     * @param email optional email
     * @param phoneNumber optional phone number
     * @param birthDay optional birth date
     * @return a list of {@link PartiesEntity} instances that match the search criteria
     */
	@Query("""
				SELECT p FROM PartiesEntity p
				WHERE (:name IS NULL OR p.name = :name)
			   	AND (:surname IS NULL OR p.surname = :surname)
				AND (:street IS NULL OR p.street = :street)
				AND (:email IS NULL OR p	.email = :email)
			  	AND (:phoneNumber IS NULL OR p.phoneNumber = :phoneNumber)
			   	AND (:birthDay IS NULL OR p.birthDay = :birthDay)
			""")
	List<PartiesEntity> searchParties(
			@Param("name") String name,
			@Param("surname") String surname,
	   		@Param("street") String street,
	   		@Param("email") String email,
	    	@Param("phoneNumber") String phoneNumber,
	        @Param("birthDay") LocalDate birthDay
		);
}