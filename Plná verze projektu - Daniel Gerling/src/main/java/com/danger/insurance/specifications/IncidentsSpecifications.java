package com.danger.insurance.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;

import jakarta.persistence.criteria.Predicate;

public class IncidentsSpecifications {

	@SuppressWarnings("unused")
	public static Specification<IncidentsEntity> dynamicIncidentSearch(String caseNumber, String birthNumber, String title, IncidentType incidentType, InsurancesSubjects incidentSubject, IncidentStatus currentStatus, LocalDate accidentDate) {
        
		return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (caseNumber != null) {
                predicates.add(cb.equal(root.get("caseNumber"), caseNumber));
            }
            
            if (birthNumber != null) {
                predicates.add(cb.equal(root.get("birthNumber"), birthNumber));
            }
            
            if (title != null) {
                predicates.add(cb.equal(root.get("title"), title));
            }
            
            if (incidentType != null) {
                predicates.add(cb.equal(root.get("incidentType"), incidentType));
            }
            
            if (incidentSubject != null) {
                predicates.add(cb.equal(root.get("incidentSubject"), incidentSubject));
            }

            if (currentStatus != null) {
                predicates.add(cb.equal(root.get("currentStatus"), currentStatus));
            }

            if (accidentDate != null) {
                predicates.add(cb.equal(root.get("accidentDate"), accidentDate));
            }

            // If no fields were provided, return an always-false predicate
            if (predicates.isEmpty()) {
                return cb.disjunction(); 				// prevents returning all entries
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }
	
}