package com.danger.insurance.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.danger.insurance.parties.data.entities.PartiesEntity;

import jakarta.persistence.criteria.Predicate;

public class PartiesSpecifications {

	@SuppressWarnings("unused")
	public static Specification<PartiesEntity> dynamicPartySearch(String name, String surname, String street, String email, String phoneNumber, LocalDate birthDay, String birthNumber) {
        
		return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if (surname != null) {
                predicates.add(cb.equal(root.get("surname"), surname));
            }
            if (street != null) {
                predicates.add(cb.equal(root.get("street"), street));
            }
            if (email != null) {
                predicates.add(cb.equal(root.get("email"), email));
            }
            if (phoneNumber != null) {
                predicates.add(cb.equal(root.get("phoneNumber"), phoneNumber));
            }
            if (birthDay != null) {
                predicates.add(cb.equal(root.get("birthDay"), birthDay));
            }
            if (birthNumber != null) {
                predicates.add(cb.equal(root.get("birthNumber"), birthNumber));
            }

            // If no fields were provided, return an always-false predicate
            if (predicates.isEmpty()) {
                return cb.disjunction(); 				// prevents returning all entries
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }
	
}