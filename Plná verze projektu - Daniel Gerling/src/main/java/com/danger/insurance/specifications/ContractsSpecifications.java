package com.danger.insurance.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.danger.insurance.insurances.contracts.data.entities.ContractsEntity;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.insurances.data.enums.InsurancesType;

import jakarta.persistence.criteria.Predicate;

public class ContractsSpecifications {

	@SuppressWarnings("unused")
	public static Specification<ContractsEntity> dynamicContractSearch(String contractNumber, InsurancesSubjects insuredSubject, InsurancesType insuranceType, LocalDate beginDate, LocalDate signatureDate) {
        
		return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (contractNumber != null) {
                predicates.add(cb.equal(root.get("contractNumber"), contractNumber));
            }
            if (insuredSubject != null) {
                predicates.add(cb.equal(root.get("insuredSubject"), insuredSubject));
            }
            if (insuranceType != null) {
                predicates.add(cb.equal(root.get("insuranceType"), insuranceType));
            }
            if (beginDate != null) {
                predicates.add(cb.equal(root.get("beginDate"), beginDate));
            }
            if (signatureDate != null) {
                predicates.add(cb.equal(root.get("signatureDate"), signatureDate));
            }

            // If no fields were provided, return an always-false predicate
            if (predicates.isEmpty()) {
                return cb.disjunction(); 				// prevents returning all entries
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }
	
}